package com.jusoft.bookingengine.component.timer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

import static com.jusoft.bookingengine.util.DateUtils.convertToSystemZone;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SystemLocalTime implements Comparable<SystemLocalTime>, Temporal {

  @NonNull
  private final LocalTime localTime;

  public static SystemLocalTime ofToday(String sourceLocalTime, ZoneId sourceZone, Clock clock) {
    Objects.requireNonNull(sourceLocalTime);
    return ofToday(LocalTime.parse(sourceLocalTime), sourceZone, clock);
  }

  public static SystemLocalTime ofToday(Clock systemClock) {
    return ofToday(LocalTime.now(systemClock), systemClock.getZone(), systemClock);
  }

  public static SystemLocalTime ofToday(LocalTime sourceLocalTime, ZoneId sourceZone, Clock clock) {
    Objects.requireNonNull(sourceLocalTime);
    Objects.requireNonNull(sourceZone);
    Objects.requireNonNull(clock);
    LocalTime systemLocalTime = sourceZone.equals(clock.getZone())
      ? sourceLocalTime
      : convertToSystemZone(sourceLocalTime, sourceZone, clock).toLocalTime();
    return new SystemLocalTime(systemLocalTime);
  }

  /**
   * Returns whether the given {@link SystemLocalTime} is equal. The local time is guaranteed to come from the same zone
   *
   * @param systemLocalTime the time to compare
   * @return whether they are equal
   */
  @Override
  public int compareTo(SystemLocalTime systemLocalTime) {
    Objects.requireNonNull(systemLocalTime);
    return this.localTime.compareTo(systemLocalTime.localTime);
  }

  @Override
  public boolean isSupported(TemporalUnit unit) {
    return this.localTime.isSupported(unit);
  }

  @Override
  public Temporal with(TemporalField field, long newValue) {
    return this.localTime.with(field, newValue);
  }

  @Override
  public Temporal plus(long amountToAdd, TemporalUnit unit) {
    return this.localTime.plus(amountToAdd, unit);
  }

  @Override
  public long until(Temporal endExclusive, TemporalUnit unit) {
    return this.localTime.until(endExclusive, unit);
  }

  @Override
  public boolean isSupported(TemporalField field) {
    return this.localTime.isSupported(field);
  }

  @Override
  public long getLong(TemporalField field) {
    return this.localTime.getLong(field);
  }

  public SystemLocalTime plusMinutes(long minutes) {
    return new SystemLocalTime(localTime.plusMinutes(minutes));
  }

  public boolean isAfter(SystemLocalTime localTime) {
    return this.localTime.isAfter(localTime.getLocalTime());
  }

  public boolean isBefore(SystemLocalTime localTime) {
    return this.localTime.isBefore(localTime.getLocalTime());
  }
}
