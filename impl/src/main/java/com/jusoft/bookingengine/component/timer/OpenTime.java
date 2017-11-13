package com.jusoft.bookingengine.component.timer;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class OpenTime implements Comparable<OpenTime> {

  @NonNull
  private final LocalTime startTime;
  @NonNull
  private final LocalTime endTime;

  //TODO test
  @Override
  public int compareTo(OpenTime other) {
    return startTime.compareTo(other.getStartTime());
  }
}
