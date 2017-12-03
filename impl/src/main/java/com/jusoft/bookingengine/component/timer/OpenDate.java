package com.jusoft.bookingengine.component.timer;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class OpenDate implements Comparable<OpenDate> {

  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;

  //TODO test
  @Override
  public int compareTo(OpenDate other) {
    return startTime.compareTo(other.getStartTime());
  }
}
