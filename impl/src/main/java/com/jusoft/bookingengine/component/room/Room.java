package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
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

import static com.jusoft.bookingengine.component.timer.TimeConstants.UTC;

@Data
public class Room {

  private final long id;
  private final int maxSlots; //TODO define this as a strategy to define how many slots can be available at all times
  private final int slotDurationInMinutes;
  private final List<OpenTime> openTimesPerDay;
  private final List<DayOfWeek> availableDays;
  private final boolean active;
  private final AuctionConfigInfo auctionConfigInfo;

  //TODO provide tests validation
  Room(long id, int maxSlots, int slotDurationInMinutes, List<OpenTime> openTimesPerDay, List<DayOfWeek> availableDays,
       boolean active, AuctionConfigInfo auctionConfigInfo) {
    Validate.notEmpty(openTimesPerDay);
    Validate.notEmpty(availableDays);
    validateOpenTimesForSlotDuration(openTimesPerDay, slotDurationInMinutes);
    this.id = id;
    this.maxSlots = maxSlots;
    this.slotDurationInMinutes = slotDurationInMinutes;
    Collections.sort(openTimesPerDay); //Needed to ensure soonest open time is checked first
    this.openTimesPerDay = openTimesPerDay;
    this.availableDays = availableDays;
    this.active = active;
    this.auctionConfigInfo = auctionConfigInfo;
  }

  private void validateOpenTimesForSlotDuration(List<OpenTime> openTimesPerDay, int slotDurationInMinutes) {
    openTimesPerDay.forEach(openTime -> {
      long minutesInOpenTime = openTime.getStartTime().until(openTime.getEndTime(), ChronoUnit.MINUTES);
      Validate.isTrue(minutesInOpenTime % slotDurationInMinutes == 0,
        "Open time not valid: startTime=&s, endTime=%s",
        openTime.getStartTime(), openTime.getEndTime());
    });
  }

  //FIXME avoid many calls to database by finding all first slots to create in one go rather than launching the
  //FIXME creation of each individual one
  //FIXME provide different strategies to decide when the next slot should be created. Current one waits for the first slot to finish
  ZonedDateTime findUpcomingSlot(Clock clock, int currentNumberOfSlotsOpen, ZonedDateTime nextSlotToFinishEndDate) {
    ZonedDateTime creationTime = ZonedDateTime.now(clock);
    if (currentNumberOfSlotsOpen >= maxSlots) {
      creationTime = nextSlotToFinishEndDate;
    }
    return creationTime;
  }

  OpenDate findFirstSlotDate(Clock clock) {
    ZonedDateTime endDate = getLastSlotEndTime(clock);
    return getNextSlotDate(endDate, clock);
  }

  private ZonedDateTime getLastSlotEndTime(Clock clock) {
    ZonedDateTime endDate;
    LocalTime currentTime = LocalTime.now(clock);
    OpenTime openTime = findOpenTimeFor(currentTime);
    if (isNotWithinOpenTime(currentTime, openTime)) {
      endDate = getStartOfNextOpenTime(openTime, clock);
    } else {
      endDate = findSlotWithin(openTime, currentTime, clock);
    }
    return endDate;
  }

  OpenDate findNextSlotDate(ZonedDateTime lastSlotEndTime, Clock clock) {
    return getNextSlotDate(lastSlotEndTime, clock);
  }

  private OpenDate getNextSlotDate(ZonedDateTime endDate, Clock clock) {
    ZonedDateTime nextSlotEndTime = getNextSlotEndTime(endDate, clock);
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

  private ZonedDateTime getStartOfNextOpenTime(OpenTime openTime, Clock clock) {
    return ZonedDateTime.of(getNextSlotLocalDate(ZonedDateTime.now(clock), openTime.getStartTime()), openTime.getStartTime(), UTC);
  }

  private ZonedDateTime findSlotWithin(OpenTime openTime, LocalTime now, Clock clock) {
    long minutesFromStartToNow = openTime.getStartTime().until(now, ChronoUnit.MINUTES);
    double slotsToNow = (double) minutesFromStartToNow / slotDurationInMinutes;
    int nextSlotNumber = findNextSlotNumber(slotsToNow);
    return ZonedDateTime.of(LocalDate.now(clock), findLocalTimeForSlot(openTime, nextSlotNumber), clock.getZone());
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

  private LocalTime findLocalTimeForSlot(OpenTime openTime, int nextSlotNumber) {
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
}
