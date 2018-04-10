package com.jusoft.bookingengine.component.timer;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

import static java.time.LocalTime.parse;

@Data(staticConstructor = "of")
public class OpenTime {

  @NonNull
  private final LocalTime startTime;
  @NonNull
  private final LocalTime endTime;

  public static OpenTime of(String startTime, String endTime) {
    return new OpenTime(parse(startTime), parse(endTime));
  }
}
