package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.NextSlotConfig;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static com.jusoft.bookingengine.component.slot.api.SlotState.AVAILABLE;
import static com.jusoft.bookingengine.component.slot.api.SlotState.IN_AUCTION;

@Data
class Room {

  private final long id;
  private final long clubId;
  private final long buildingId;
  private final SlotCreationConfigInfo slotCreationConfigInfo;
  private final int slotDurationInMinutes;
  private final List<OpenTime> openTimesPerDay;
  private final List<DayOfWeek> availableDays;
  private final AuctionConfigInfo auctionConfigInfo;

  Room(long id, long clubId, long buildingId, SlotCreationConfigInfo slotCreationConfigInfo, int slotDurationInMinutes, List<OpenTime> openTimesPerDay, List<DayOfWeek> availableDays, AuctionConfigInfo auctionConfigInfo) {
    Validate.notNull(auctionConfigInfo);
    Validate.notNull(slotCreationConfigInfo);
    Validate.notEmpty(openTimesPerDay);
    Validate.notEmpty(availableDays);
    openTimesPerDay.sort(Comparator.comparing(OpenTime::getStartTime)); //Needed to ensure soonest open time is checked first
    this.id = id;
    this.clubId = clubId;
    this.buildingId = buildingId;
    this.slotCreationConfigInfo = slotCreationConfigInfo;
    this.slotDurationInMinutes = slotDurationInMinutes;
    this.openTimesPerDay = new ArrayList<>(openTimesPerDay);
    this.availableDays = new ArrayList<>(availableDays);
    this.auctionConfigInfo = auctionConfigInfo;
    validateOpenTimesDurationAreMultiplesOfSlotDuration(openTimesPerDay, slotDurationInMinutes);
  }

  private void validateOpenTimesDurationAreMultiplesOfSlotDuration(List<OpenTime> openTimesPerDay, int slotDurationInMinutes) {
    openTimesPerDay.forEach(openTime -> {
      long minutesInOpenTime = openTime.getStartTime().until(openTime.getEndTime(), ChronoUnit.MINUTES);
      Validate.isTrue(minutesInOpenTime % slotDurationInMinutes == 0,
        "Open time not valid: startTime=%s, endTime=%s",
        openTime.getStartTime(), openTime.getEndTime());
    });
  }

  NextSlotConfig findFirstSlotDate(Clock clock) {
    ZonedDateTime endDate = findNextSlotStartTimeFromNow(clock);
    return findNextSlotDate(endDate, clock);
  }

  private ZonedDateTime findNextSlotStartTimeFromNow(Clock clock) {
    ZonedDateTime previousSlotEndTime;
    LocalTime currentTime = LocalTime.now(clock);
    OpenTime openTime = findOpenTimeFor(currentTime);
    if (isNotWithinOpenTime(currentTime, openTime)) {
      previousSlotEndTime = getStartOfNextOpenTime(openTime, clock);
    } else {
      previousSlotEndTime = findPreviousSlotEndTimeWithin(openTime, clock);
    }
    return previousSlotEndTime;
  }

  NextSlotConfig findNextSlotDate(ZonedDateTime lastSlotEndTime, Clock clock) {
    ZonedDateTime nextSlotEndTime = getNextSlotEndTime(lastSlotEndTime, clock);
    OpenDate openDate = OpenDate.of(nextSlotEndTime.minusMinutes(slotDurationInMinutes), nextSlotEndTime);
    return new NextSlotConfig(buildingId, clubId, openDate, getSlotInitialState());
  }

  private SlotState getSlotInitialState() {
    return isAuctionRequired() ? IN_AUCTION : AVAILABLE;
  }

  private OpenTime findOpenTimeFor(LocalTime localTime) {
    return openTimesPerDay.stream()
      .filter(isOpenTimeIncluding(localTime))
      .findFirst()
      .orElse(findClosestOpenTimeFor(localTime));
  }

  private boolean isNotWithinOpenTime(LocalTime currentTime, OpenTime openTime) {
    return currentTime.isBefore(openTime.getStartTime()) || currentTime.isAfter(openTime.getEndTime());
  }

  private ZonedDateTime getStartOfNextOpenTime(OpenTime openTime, Clock clock) {
    return ZonedDateTime.of(getNextSlotLocalDate(ZonedDateTime.now(clock), openTime.getStartTime()), openTime.getStartTime(), clock.getZone());
  }

  private ZonedDateTime findPreviousSlotEndTimeWithin(OpenTime openTime, Clock clock) {
    long minutesFromStartToNow = openTime.getStartTime().until(LocalTime.now(clock), ChronoUnit.MINUTES);
    double slotsToNow = (double) minutesFromStartToNow / slotDurationInMinutes;
    int nextSlotNumber = findNextSlotNumber(slotsToNow);
    return ZonedDateTime.of(LocalDate.now(clock), findSlotStartTimeFor(openTime, nextSlotNumber), clock.getZone());
  }

  private int findNextSlotNumber(double slotsToNow) {
    int slotToStart;
    if (isCurrentSlotStarting(slotsToNow)) {
      slotToStart = (int) slotsToNow;
    } else {
      slotToStart = (int) Math.ceil(slotsToNow);
    }
    return slotToStart;
  }

  private boolean isCurrentSlotStarting(double slotsToNow) {
    return slotsToNow % 1 == 0;
  }

  private LocalTime findSlotStartTimeFor(OpenTime openTime, int nextSlotNumber) {
    return openTime.getStartTime().plusMinutes(nextSlotNumber * (long) slotDurationInMinutes);
  }

  private Predicate<OpenTime> isOpenTimeIncluding(LocalTime localTime) {
    return openTime -> localTime.isAfter(openTime.getStartTime()) && localTime.isBefore(openTime.getEndTime());
  }

  //Relies on the order of openTimesPerDay list
  private OpenTime findClosestOpenTimeFor(LocalTime now) {
    OpenTime closestOpenTime = null;
    for (OpenTime openTime : new LinkedList<>(openTimesPerDay)) {
      if (now.isBefore(openTime.getEndTime()) || now.equals(openTime.getEndTime())) {
        closestOpenTime = openTime;
        break;
      }
    }
    //In case current time is after the room is closed for the rest of the day, choose open of the next day
    if (closestOpenTime == null) {
      closestOpenTime = openTimesPerDay.get(0);
    }
    return closestOpenTime;
  }

  private ZonedDateTime getNextSlotEndTime(ZonedDateTime lastSlotEndTime, Clock clock) {
    ZonedDateTime endTimeNextSlot = lastSlotEndTime.plusMinutes(slotDurationInMinutes);
    OpenTime closestOpenTimeForLastSlotEnd = findClosestOpenTimeFor(lastSlotEndTime.toLocalTime());
    OpenTime closestOpenTimeForSlotNextEnd = findClosestOpenTimeFor(endTimeNextSlot.toLocalTime());
    if (!isLastSlotOpenTimeSameAsNextSlotOpenTime(closestOpenTimeForLastSlotEnd, closestOpenTimeForSlotNextEnd) ||
      isNotWithinOpenTime(endTimeNextSlot.toLocalTime(), closestOpenTimeForSlotNextEnd)) {
      LocalDate nextSlotLocalDate = getNextSlotLocalDate(endTimeNextSlot, closestOpenTimeForSlotNextEnd.getStartTime());
      endTimeNextSlot = ZonedDateTime.of(nextSlotLocalDate, closestOpenTimeForSlotNextEnd.getStartTime().plusMinutes(slotDurationInMinutes), clock.getZone());
    }
    return endTimeNextSlot;
  }

  private LocalDate getNextSlotLocalDate(ZonedDateTime endTimeNextSlot, LocalTime openTimeStart) {
    return endTimeNextSlot.toLocalTime().isBefore(openTimeStart) ? endTimeNextSlot.toLocalDate() : endTimeNextSlot.toLocalDate().plusDays(1);
  }

  private boolean isLastSlotOpenTimeSameAsNextSlotOpenTime(OpenTime closestOpenTimeForLastSlotEnd, OpenTime closestOpenTimeForSlotNextEnd) {
    return closestOpenTimeForLastSlotEnd.getStartTime().equals(closestOpenTimeForSlotNextEnd.getStartTime());
  }

  public List<OpenTime> getOpenTimesPerDay() {
    return new ArrayList<>(openTimesPerDay);
  }

  public List<DayOfWeek> getAvailableDays() {
    return new ArrayList<>(availableDays);
  }

  public boolean isAuctionRequired() {
    return auctionConfigInfo.getAuctionDuration() > 0;
  }

}
