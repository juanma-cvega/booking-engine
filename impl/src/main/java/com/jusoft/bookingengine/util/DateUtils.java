package com.jusoft.bookingengine.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.LocalTime.parse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

  public static ZonedDateTime convertToSystemZone(String sourceTime, ZoneId sourceZone, Clock clock) {
    return convertToSystemZone(parse(sourceTime), sourceZone, clock);
  }

  public static ZonedDateTime convertToSystemZone(LocalTime sourceTime, ZoneId sourceZone, Clock clock) {
    return ZonedDateTime.of(LocalDate.now(clock), sourceTime, sourceZone).withZoneSameInstant(clock.getZone());
  }
}
