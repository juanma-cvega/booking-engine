package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.jusoft.bookingengine.component.timer.SystemLocalTime.ofToday;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SlotsTimetable {

  private final int slotDurationInMinutes;
  @NonNull
  private final List<OpenTime> openTimesPerDay;
  @NonNull
  private final List<DayOfWeek> availableDays;

  public static SlotsTimetable of(int slotDurationInMinutes, List<OpenTime> openTimesPerDay, List<DayOfWeek> availableDays) {
    Validate.notEmpty(openTimesPerDay);
    Validate.notEmpty(availableDays);
    return new SlotsTimetable(
      slotDurationInMinutes,
      new ArrayList<>(openTimesPerDay),
      new ArrayList<>(availableDays)
    );
  }

  public boolean isCovered(ReservedSlotsOfDay reservedSlotsOfDays) {
    return availableDays.contains(reservedSlotsOfDays.getDayOfWeek()) && reservedSlotsOfDays.getSlotsStartTime().stream().allMatch(this::isTimeValid);
  }

  /**
   * Returns whether a given {@link ZonedDateTime} is covered
   *
   * @param date The date to verify. IMPORTANT: it's transformed to use {@link ZoneId} UTC before start validation.
   * @return whether the given parameters are covered
   */
  public boolean isCovered(ZonedDateTime date, Clock clock) {
    return isCovered(date.getDayOfWeek(), ofToday(date.toLocalTime(), date.getZone(), clock));
  }

  /**
   * Returns whether a given {@link DayOfWeek} and {@link LocalTime} are covered
   *
   * @param dayOfWeek {@link DayOfWeek} to verify
   * @param localTime {@link LocalTime} to verify. IMPORTANT: it's considered to be a {@link LocalTime} related to UTC
   * @return whether the given parameters are covered
   */
  private boolean isCovered(DayOfWeek dayOfWeek, SystemLocalTime localTime) {
    return availableDays.contains(dayOfWeek) && isTimeValid(localTime);
  }

  private boolean isTimeValid(SystemLocalTime localTime) {
    return openTimesPerDay.stream()
      .anyMatch(openTime -> isStartTimeValidFor(localTime, openTime));
  }

  private boolean isStartTimeValidFor(SystemLocalTime startTime, OpenTime openTime) {
    return isStartTimeWithinOpenTime(startTime, openTime) && isStartTimeAtStartOfSlot(startTime, openTime);
  }

  private boolean isStartTimeWithinOpenTime(SystemLocalTime startTime, OpenTime openTime) {
    return startTime.compareTo(openTime.getStartTime()) >= 0 && startTime.compareTo(openTime.getEndTime()) <= 0;
  }

  private boolean isStartTimeAtStartOfSlot(SystemLocalTime reservationTime, OpenTime openTime) {
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
