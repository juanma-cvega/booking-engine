package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableOverlappingException;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservationNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservationDateNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotAlreadyTakenException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetableInvalidException;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import lombok.Data;
import lombok.NonNull;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.CLASS;
import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.PERSON;
import static java.time.ZonedDateTime.now;
import static java.util.stream.Collectors.toList;

@Data
class SlotLifeCycleManager {

  private final long roomId;
  @NonNull
  private final SlotsTimetable slotsTimetable;
  @NonNull
  private final AuctionConfigInfo auctionConfigInfo;
  @NonNull
  private final Map<Long, ClassTimetable> classesTimetable;
  @NonNull
  private final List<PreReservation> preReservations;

  SlotLifeCycleManager(long roomId, SlotsTimetable slotsTimetable) {
    this(roomId, slotsTimetable, NoAuctionConfigInfo.getInstance(), new HashMap<>(), new ArrayList<>());
  }

  SlotLifeCycleManager(long roomId, SlotsTimetable slotsTimetable, AuctionConfigInfo auctionConfigInfo, Map<Long, ClassTimetable> classesTimetable, List<PreReservation> preReservations) {
    this.roomId = roomId;
    this.slotsTimetable = slotsTimetable;
    this.auctionConfigInfo = auctionConfigInfo;
    this.classesTimetable = classesTimetable;
    this.preReservations = preReservations;
  }

  NextSlotState nextStateFor(long slotId, ZonedDateTime slotStartTime, Clock clock) {
    //The order of suppliers defines the preferences in terms of finding the next state for a slot according to the reservation logic
    return Stream.of(
      isReservedForClassState(slotId, slotStartTime, clock),
      isPreReservedState(slotId, slotStartTime),
      isInAuctionState(slotId)
    ).map(Supplier::get)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .findFirst()
      .orElse(AvailableState.of(slotId));
  }

  private Supplier<Optional<NextSlotState>> isReservedForClassState(long slotId, ZonedDateTime startTime, Clock clock) {
    return () -> classesTimetable.values().stream()
      .filter(currentClass -> currentClass.contains(startTime, clock))
      .findFirst()
      .map(classFound -> PreReservedState.of(slotId, SlotUser.of(classFound.getClassId(), CLASS)));
  }

  private Supplier<Optional<NextSlotState>> isPreReservedState(long slotId, ZonedDateTime slotStartTime) {
    return () -> preReservations.stream()
      .filter(preReservation -> preReservation.getReservationDate().isEqual(slotStartTime))
      .findFirst()
      .map(preReservation -> PreReservedState.of(slotId, SlotUser.of(preReservation.getUserId(), PERSON)));
  }

  private Supplier<Optional<NextSlotState>> isInAuctionState(long slotId) {
    return () -> auctionConfigInfo.getAuctionDuration() > 0
      ? Optional.of(InAuctionState.of(slotId, auctionConfigInfo))
      : Optional.empty();
  }

  SlotLifeCycleManager replaceAuctionConfigWith(AuctionConfigInfo auctionConfigInfo) {
    return new SlotLifeCycleManager(roomId, slotsTimetable, auctionConfigInfo, classesTimetable, preReservations);
  }

  SlotLifeCycleManager addClass(ClassTimetable classTimetable, Clock clock) {
    if (!areClassReservedSlotsCovered(classTimetable)) {
      throw new ClassTimetableNotValidException(roomId, classTimetable);
    }
    if (isPreReservationWithinClassTimetable(classTimetable, clock) || isClassTimetableOverlappingAnyClass(classTimetable)) {
      throw new ClassTimetableOverlappingException(roomId, classTimetable);
    }
    classesTimetable.put(classTimetable.getClassId(), classTimetable);
    return new SlotLifeCycleManager(roomId, slotsTimetable, auctionConfigInfo, classesTimetable, preReservations);
  }

  private boolean areClassReservedSlotsCovered(ClassTimetable classTimetable) {
    return classTimetable.getReservedSlotsOfDays().stream().allMatch(slotsTimetable::isCovered);
  }

  private boolean isClassTimetableOverlappingAnyClass(ClassTimetable classTimetable) {
    return classesTimetable.values().stream()
      .anyMatch(configuredClassTimetable -> configuredClassTimetable.intersectsWith(classTimetable.getReservedSlotsOfDays()));
  }

  private boolean isPreReservationWithinClassTimetable(ClassTimetable classTimetable, Clock clock) {
    return preReservations.stream()
      .map(PreReservation::getReservationDate)
      .anyMatch(reservationDate -> classTimetable.contains(reservationDate, clock));
  }

  SlotLifeCycleManager removeClass(long classId) {
    ClassTimetable classRemoved = classesTimetable.remove(classId);
    if (classRemoved == null) {
      throw new ClassTimetableNotFoundException(classId, roomId);
    }
    return new SlotLifeCycleManager(roomId, slotsTimetable, auctionConfigInfo, classesTimetable, preReservations);
  }

  SlotLifeCycleManager addPreReservation(PreReservation preReservation, Clock clock) {
    preReservations.removeAll(findPassedPreReservations(clock)); //Cleans up the list to avoid iterating through stale data
    if (!slotsTimetable.isCovered(preReservation.getReservationDate(), clock)) {
      throw new ReservationDateNotValidException(preReservation.getReservationDate(), roomId);
    }
    if (isReservationDateAlreadyTaken(preReservation.getReservationDate(), clock)) {
      throw new SlotAlreadyTakenException(preReservation.getReservationDate());
    }
    preReservations.add(preReservation);
    return new SlotLifeCycleManager(roomId, slotsTimetable, auctionConfigInfo, classesTimetable, preReservations);
  }

  private List<PreReservation> findPassedPreReservations(Clock clock) {
    return preReservations.stream()
      .filter(preReservation -> preReservation.getReservationDate().isBefore(now(clock)))
      .collect(toList());
  }

  private boolean isReservationDateAlreadyTaken(ZonedDateTime reservationDate, Clock clock) {
    return isSlotTimeAlreadyPreReserved(reservationDate) || anyClassContains(reservationDate, clock);
  }

  private boolean isSlotTimeAlreadyPreReserved(ZonedDateTime slotStartTime) {
    return preReservations.stream()
      .anyMatch(preReservation -> preReservation.getReservationDate().isEqual(slotStartTime));
  }

  SlotLifeCycleManager removePreReservation(ZonedDateTime slotStartTime) {
    PreReservation preReservationFound = preReservations.stream()
      .filter(preReservation -> preReservation.getReservationDate().isEqual(slotStartTime))
      .findFirst()
      .orElseThrow(() -> new PreReservationNotFoundException(roomId, slotStartTime));
    preReservations.remove(preReservationFound);
    return new SlotLifeCycleManager(roomId, slotsTimetable, auctionConfigInfo, classesTimetable, preReservations);
  }

  private boolean anyClassContains(ZonedDateTime reservationDate, Clock clock) {
    return classesTimetable.values().stream()
      .anyMatch(classTimetable -> classTimetable.getReservedSlotsOfDays().stream()
        .anyMatch(reservedSlotsOfDay -> reservedSlotsOfDay.contains(reservationDate, clock)));
  }

  SlotLifeCycleManager replaceSlotsTimetableWith(SlotsTimetable slotsTimetable, Clock clock) {
    if (!areCurrentPreReservationsCoveredBy(slotsTimetable, clock) || !areCurrentClassesCoveredBy(slotsTimetable)) {
      SlotsTimetable slotsTimetableDetails = SlotsTimetable.of(slotsTimetable.getSlotDurationInMinutes(), slotsTimetable.getOpenTimesPerDay(), slotsTimetable.getAvailableDays());
      throw new SlotsTimetableInvalidException(roomId, slotsTimetableDetails);
    }
    return new SlotLifeCycleManager(roomId, slotsTimetable, auctionConfigInfo, classesTimetable, preReservations);
  }

  private boolean areCurrentPreReservationsCoveredBy(SlotsTimetable slotsTimetable, Clock clock) {
    return preReservations.stream().map(PreReservation::getReservationDate).allMatch(reservationDate -> slotsTimetable.isCovered(reservationDate, clock));
  }

  private boolean areCurrentClassesCoveredBy(SlotsTimetable slotsTimetable) {
    return classesTimetable.values().stream()
      .flatMap(classTimetable -> classTimetable.getReservedSlotsOfDays().stream())
      .allMatch(slotsTimetable::isCovered);
  }

  List<PreReservation> getPreReservations() {
    return new ArrayList<>(preReservations);
  }

  Map<Long, ClassTimetable> getClassesTimeTable() {
    return new HashMap<>(classesTimetable);
  }
}
