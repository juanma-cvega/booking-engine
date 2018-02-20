package com.jusoft.bookingengine.component.timer;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class OpenTime {

  @NonNull
  private final LocalTime startTime;
  @NonNull
  private final LocalTime endTime;

}
