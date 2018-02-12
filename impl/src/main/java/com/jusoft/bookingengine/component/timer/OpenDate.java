package com.jusoft.bookingengine.component.timer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenDate {

  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;

  public static OpenDate of(ZonedDateTime startTime, ZonedDateTime endTime) {
    return new OpenDate(startTime, endTime);
  }
}
