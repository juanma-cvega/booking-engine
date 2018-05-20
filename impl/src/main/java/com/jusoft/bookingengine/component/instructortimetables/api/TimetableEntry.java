package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;

@Data
public class TimetableEntry {

  private final long buildingId;
  private final long roomId;
  @NonNull
  private final String classType;
  @NonNull
  private final DayOfWeek dayOfWeek;
  @NonNull
  private final OpenTime openTime;
}
