package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data(staticConstructor = "of")
public class SlotValidationInfo {

  private final int slotDurationInMinutes;
  @NonNull
  private final List<OpenTime> openTimesPerDay;
  @NonNull
  private final List<DayOfWeek> availableDays;

  public boolean isCovered(ReservedSlotsOfDay reservedSlotsOfDays) {
    return areCovered(reservedSlotsOfDays.getDayOfWeek(), reservedSlotsOfDays.getSlotsStartTime());
  }

  /**
   * Returns whether a given {@link ZonedDateTime} is covered
   *
   * @param date The date to verify. IMPORTANT: it's transformed to use {@link ZoneId} UTC before start validation.
   * @return whether the given parameters are covered
   */
  public boolean isCovered(ZonedDateTime date) {
    return isCovered(date.getDayOfWeek(), date.withZoneSameInstant(Clock.systemUTC().getZone()).toLocalTime());
  }

  /**
   * Returns whether a given {@link DayOfWeek} and {@link LocalTime} are covered
   *
   * @param dayOfWeek {@link DayOfWeek} to verify
   * @param localTime {@link LocalTime} to verify. IMPORTANT: it's considered to be a {@link LocalTime} related to UTC
   * @return whether the given parameters are covered
   */
  public boolean isCovered(DayOfWeek dayOfWeek, LocalTime localTime) {
    return availableDays.contains(dayOfWeek) && isTimeValid(localTime);
  }

  /**
   * Returns whether a given {@link DayOfWeek} and list of {@link LocalTime} are covered
   *
   * @param dayOfWeek  {@link DayOfWeek} to verify
   * @param localTimes {@link LocalTime}s to verify. IMPORTANT: it's considered to be a {@link LocalTime} related to UTC
   * @return whether the given parameters are covered
   */
  public boolean areCovered(DayOfWeek dayOfWeek, List<LocalTime> localTimes) {
    return availableDays.contains(dayOfWeek) && localTimes.stream().allMatch(this::isTimeValid);
  }

  private boolean isTimeValid(LocalTime localTime) {
    return openTimesPerDay.stream()
      .anyMatch(openTime -> isStartTimeValidFor(localTime, openTime));
  }

  private boolean isStartTimeValidFor(LocalTime startTime, OpenTime openTime) {
    return isStartTimeWithinOpenTime(startTime, openTime) && isStartTimeAtStartOfSlot(startTime, openTime);
  }

  private boolean isStartTimeWithinOpenTime(LocalTime startTime, OpenTime openTime) {
    return startTime.compareTo(openTime.getStartTime()) >= 0 && startTime.compareTo(openTime.getEndTime()) <= 0;
  }

  private boolean isStartTimeAtStartOfSlot(LocalTime reservationTime, OpenTime openTime) {
    long timeUntilEndOpenTime = reservationTime.until(openTime.getEndTime(), ChronoUnit.MINUTES);
    return timeUntilEndOpenTime > 0 && timeUntilEndOpenTime % slotDurationInMinutes == 0;
  }

  public List<OpenTime> getOpenTimesPerDay() {
    return new ArrayList<>(openTimesPerDay);
  }

  public List<DayOfWeek> getAvailableDays() {
    return new ArrayList<>(availableDays);
  }
}
