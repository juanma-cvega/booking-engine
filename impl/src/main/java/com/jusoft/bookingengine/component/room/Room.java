package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Data
class Room {

  private final long id;
  private final long buildingId;
  private final SlotCreationConfigInfo slotCreationConfigInfo;
  private final int slotDurationInMinutes;
  private final List<OpenTime> openTimesPerDay;
  private final List<DayOfWeek> availableDays;
  private final boolean active;
  private final AuctionConfigInfo auctionConfigInfo;
  @Getter(value = AccessLevel.PRIVATE)
  private final Clock clock;

  Room(long id, long buildingId, SlotCreationConfigInfo slotCreationConfigInfo, int slotDurationInMinutes, List<OpenTime> openTimesPerDay, List<DayOfWeek> availableDays,
       boolean active, AuctionConfigInfo auctionConfigInfo, Clock clock) {
    this.id = id;
    this.buildingId = buildingId;
    this.slotCreationConfigInfo = slotCreationConfigInfo;
    this.slotDurationInMinutes = slotDurationInMinutes;
    this.openTimesPerDay = openTimesPerDay;
    Collections.sort(this.openTimesPerDay); //Needed to ensure soonest open time is checked first
    this.availableDays = availableDays;
    this.active = active;
    this.auctionConfigInfo = auctionConfigInfo;
    this.clock = clock;
    Validate.notNull(this.auctionConfigInfo);
    Validate.notNull(this.slotCreationConfigInfo);
    Validate.notEmpty(this.openTimesPerDay);
    Validate.notEmpty(this.availableDays);
    Validate.notNull(this.clock);
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

  OpenDate findFirstSlotDate() {
    ZonedDateTime endDate = findNextSlotStartTimeFromNow();
    return findNextSlotDate(endDate);
  }

  private ZonedDateTime findNextSlotStartTimeFromNow() {
    ZonedDateTime previousSlotEndTime;
    LocalTime currentTime = LocalTime.now(clock);
    OpenTime openTime = findOpenTimeFor(currentTime);
    if (isNotWithinOpenTime(currentTime, openTime)) {
      previousSlotEndTime = getStartOfNextOpenTime(openTime);
    } else {
      previousSlotEndTime = findPreviousSlotEndTimeWithin(openTime);
    }
    return previousSlotEndTime;
  }

  OpenDate findNextSlotDate(ZonedDateTime lastSlotEndTime) {
    ZonedDateTime nextSlotEndTime = getNextSlotEndTime(lastSlotEndTime);
    return new OpenDate(nextSlotEndTime.minusMinutes(slotDurationInMinutes), nextSlotEndTime);
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

  private ZonedDateTime getStartOfNextOpenTime(OpenTime openTime) {
    return ZonedDateTime.of(getNextSlotLocalDate(ZonedDateTime.now(clock), openTime.getStartTime()), openTime.getStartTime(), clock.getZone());
  }

  private ZonedDateTime findPreviousSlotEndTimeWithin(OpenTime openTime) {
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
    return openTime.getStartTime().plusMinutes(nextSlotNumber * slotDurationInMinutes);
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

  private ZonedDateTime getNextSlotEndTime(ZonedDateTime lastSlotEndTime) {
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
}
