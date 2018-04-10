package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ReservedSlotsOfDay {

  private final DayOfWeek dayOfWeek;
  private final List<LocalTime> slotsStartTime;
  private final ZoneId zoneId;

  public static ReservedSlotsOfDay of(DayOfWeek dayOfWeek, List<LocalTime> slotsStartTime, LocalDate creationDate, ZoneId sourceZoneId, Clock clock) {
    Objects.requireNonNull(dayOfWeek);
    Validate.notEmpty(slotsStartTime);
    Objects.requireNonNull(sourceZoneId);
    Objects.requireNonNull(creationDate);
    Objects.requireNonNull(clock);
    List<LocalTime> slotsStartTimeToSystemZone = toSystemZone(slotsStartTime, creationDate, sourceZoneId, clock);
    return new ReservedSlotsOfDay(dayOfWeek, slotsStartTimeToSystemZone, clock.getZone());
  }

  private static List<LocalTime> toSystemZone(List<LocalTime> slotsStartTime, LocalDate sourceDate, ZoneId sourceZoneId, Clock clock) {
    return sourceZoneId.equals(clock.getZone()) ? slotsStartTime
      : slotsStartTime.stream()
      .map(slotStartTime -> ZonedDateTime.of(sourceDate, slotStartTime, sourceZoneId).withZoneSameInstant(clock.getZone()))
      .map(ZonedDateTime::toLocalTime)
      .collect(toList());
  }

  public boolean contains(ZonedDateTime startDateTime) {
    boolean containsStartTime = false;
    if (dayOfWeek == startDateTime.getDayOfWeek()) {
      LocalTime startTime = startDateTime.withZoneSameInstant(zoneId).toLocalTime();
      containsStartTime = slotsStartTime.stream()
        .anyMatch(currentDate -> currentDate.equals(startTime));
    }
    return containsStartTime;
  }

  public boolean intersectsWith(ReservedSlotsOfDay reservedSlotsOfDay) {
    return reservedSlotsOfDay.dayOfWeek == this.dayOfWeek && isDayReservedSlotsContainedIn(reservedSlotsOfDay);
  }

  private boolean isDayReservedSlotsContainedIn(ReservedSlotsOfDay reservedSlotsOfDay) {
    List<LocalTime> slots = new ArrayList<>(reservedSlotsOfDay.slotsStartTime);
    slots.removeAll(slotsStartTime);
    return slots.size() != slotsStartTime.size();
  }

  public List<LocalTime> getSlotsStartTime() {
    return new ArrayList<>(slotsStartTime);
  }
}
