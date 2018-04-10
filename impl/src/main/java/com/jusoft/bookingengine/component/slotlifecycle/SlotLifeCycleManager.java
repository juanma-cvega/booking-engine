package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfig;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfigNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfigNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfigOverlappingException;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservationNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservationDateNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotAlreadyTakenException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInvalidException;
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

import static java.time.ZonedDateTime.now;
import static java.util.stream.Collectors.toList;

@Data
class SlotLifeCycleManager {

  private final long roomId;
  @NonNull
  private final SlotValidationInfo slotValidationInfo;
  @NonNull
  private final AuctionConfigInfo auctionConfigInfo;
  @NonNull
  private final Map<Long, ClassConfig> classesConfig;
  @NonNull
  private final List<PreReservation> preReservations;

  SlotLifeCycleManager(long roomId, SlotValidationInfo slotValidationInfo) {
    this(roomId, slotValidationInfo, new NoAuctionConfigInfo(), new HashMap<>(), new ArrayList<>());
  }

  SlotLifeCycleManager(long roomId, SlotValidationInfo slotValidationInfo, AuctionConfigInfo auctionConfigInfo, Map<Long, ClassConfig> classesConfig, List<PreReservation> preReservations) {
    this.roomId = roomId;
    this.slotValidationInfo = slotValidationInfo;
    this.auctionConfigInfo = auctionConfigInfo;
    this.classesConfig = classesConfig;
    this.preReservations = preReservations;
  }

  NextSlotState nextStateFor(long slotId, ZonedDateTime slotStartTime) {
    //The order of suppliers defines the preferences in terms of finding the next state for a slot according to the reservation logic
    return Stream.of(
      isReservedForClassState(slotId, slotStartTime),
      isPreReservedState(slotId, slotStartTime),
      isInAuctionState(slotId)
    ).map(Supplier::get)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .findFirst()
      .orElse(AvailableState.of(slotId));
  }

  private Supplier<Optional<NextSlotState>> isReservedForClassState(long slotId, ZonedDateTime startTime) {
    return () -> classesConfig.values().stream()
      .filter(currentClass -> currentClass.contains(startTime))
      .findFirst()
      .map(classFound -> ReservedForClassState.of(slotId, classFound.getClassId()));
  }

  private Supplier<Optional<NextSlotState>> isPreReservedState(long slotId, ZonedDateTime slotStartTime) {
    return () -> preReservations.stream()
      .filter(preReservation -> preReservation.getReservationDate().isEqual(slotStartTime))
      .findFirst()
      .map(preReservation -> PreReservedState.of(slotId, preReservation.getUserId()));
  }

  private Supplier<Optional<NextSlotState>> isInAuctionState(long slotId) {
    return () -> {
      Optional<NextSlotState> auctionRequired = Optional.empty();
      if (auctionConfigInfo.getAuctionDuration() > 0) {
        auctionRequired = Optional.of(InAuctionState.of(slotId, auctionConfigInfo));
      }
      return auctionRequired;
    };
  }

  SlotLifeCycleManager replaceAuctionConfigWith(AuctionConfigInfo auctionConfigInfo) {
    return new SlotLifeCycleManager(roomId, slotValidationInfo, auctionConfigInfo, classesConfig, preReservations);
  }

  SlotLifeCycleManager addClass(ClassConfig classConfig) {
    if (!areClassReservedSlotsCovered(classConfig)) {
      throw new ClassConfigNotValidException(roomId, classConfig);
    }
    if (isPreReservationWithinClassConfig(classConfig) || isClassConfigOverlappingAnyClass(classConfig)) {
      throw new ClassConfigOverlappingException(roomId, classConfig);
    }
    classesConfig.put(classConfig.getClassId(), classConfig);
    return new SlotLifeCycleManager(roomId, slotValidationInfo, auctionConfigInfo, classesConfig, preReservations);
  }

  private boolean areClassReservedSlotsCovered(ClassConfig classConfig) {
    return classConfig.getReservedSlotsOfDays().stream().allMatch(slotValidationInfo::isCovered);
  }

  private boolean isClassConfigOverlappingAnyClass(ClassConfig classConfig) {
    return classesConfig.values().stream().anyMatch(configuredClassConfig -> configuredClassConfig.intersectsWith(classConfig));
  }

  private boolean isPreReservationWithinClassConfig(ClassConfig classConfig) {
    return preReservations.stream().map(PreReservation::getReservationDate).anyMatch(classConfig::contains);
  }

  SlotLifeCycleManager removeClass(long classId) {
    ClassConfig classRemoved = classesConfig.remove(classId);
    if (classRemoved == null) {
      throw new ClassConfigNotFoundException(classId, roomId);
    }
    return new SlotLifeCycleManager(roomId, slotValidationInfo, auctionConfigInfo, classesConfig, preReservations);
  }

  SlotLifeCycleManager addPreReservation(PreReservation preReservation, Clock clock) {
    preReservations.removeAll(findPassedPreReservations(clock)); //Cleans up the list to avoid iterating through stale data
    if (!slotValidationInfo.isCovered(preReservation.getReservationDate())) {
      throw new ReservationDateNotValidException(preReservation.getReservationDate(), roomId);
    }
    if (isReservationDateAlreadyTaken(preReservation.getReservationDate())) {
      throw new SlotAlreadyTakenException(preReservation.getReservationDate());
    }
    preReservations.add(preReservation);
    return new SlotLifeCycleManager(roomId, slotValidationInfo, auctionConfigInfo, classesConfig, preReservations);
  }

  private List<PreReservation> findPassedPreReservations(Clock clock) {
    return preReservations.stream()
      .filter(preReservation -> preReservation.getReservationDate().isBefore(now(clock)))
      .collect(toList());
  }

  private boolean isReservationDateAlreadyTaken(ZonedDateTime reservationDate) {
    return isSlotTimeAlreadyPreReserved(reservationDate) || anyClassContains(reservationDate);
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
    return new SlotLifeCycleManager(roomId, slotValidationInfo, auctionConfigInfo, classesConfig, preReservations);
  }

  private boolean anyClassContains(ZonedDateTime reservationDate) {
    return classesConfig.values().stream()
      .anyMatch(classConfig -> classConfig.getReservedSlotsOfDays().stream()
        .anyMatch(dayReservedSlots -> dayReservedSlots.contains(reservationDate)));
  }

  SlotLifeCycleManager replaceSlotValidationWith(SlotValidationInfo slotValidationInfo) {
    if (!areCurrentPreReservationsCoveredBy(slotValidationInfo) || !areCurrentClassesCoveredBy(slotValidationInfo)) {
      throw new SlotValidationInvalidException(roomId, slotValidationInfo);
    }
    return new SlotLifeCycleManager(roomId, slotValidationInfo, auctionConfigInfo, classesConfig, preReservations);
  }

  private boolean areCurrentPreReservationsCoveredBy(SlotValidationInfo slotValidationInfo) {
    return preReservations.stream().map(PreReservation::getReservationDate).allMatch(slotValidationInfo::isCovered);
  }

  private boolean areCurrentClassesCoveredBy(SlotValidationInfo slotValidationInfo) {
    return classesConfig.values().stream()
      .flatMap(classConfig -> classConfig.getReservedSlotsOfDays().stream())
      .allMatch(slotValidationInfo::isCovered);
  }

  List<PreReservation> getPreReservations() {
    return new ArrayList<>(preReservations);
  }

  Map<Long, ClassConfig> getClassesConfig() {
    return new HashMap<>(classesConfig);
  }
}
