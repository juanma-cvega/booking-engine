package com.jusoft.bookingengine.component.timer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class OpenTime {

  @NonNull
  private final SystemLocalTime startTime;
  @NonNull
  private final SystemLocalTime endTime;

  public static OpenTime of(String startTime, String endTime, ZoneId sourceZone, Clock systemClock) {
    return of(SystemLocalTime.ofToday(startTime, sourceZone, systemClock), SystemLocalTime.ofToday(endTime, sourceZone, systemClock));
  }

  public static OpenTime of(LocalTime startTime, LocalTime endTime, ZoneId sourceZone, Clock systemClock) {
    return of(SystemLocalTime.ofToday(startTime, sourceZone, systemClock), SystemLocalTime.ofToday(endTime, sourceZone, systemClock));
  }

  public static OpenTime of(SystemLocalTime startTime, SystemLocalTime endTime) {
    Validate.isTrue(startTime.isBefore(endTime));
    return new OpenTime(startTime, endTime);
  }

  public boolean isOverlappingWith(OpenTime openTime) {
    return !(endTime.isBefore(openTime.startTime) || startTime.isAfter(openTime.endTime));
  }
}
