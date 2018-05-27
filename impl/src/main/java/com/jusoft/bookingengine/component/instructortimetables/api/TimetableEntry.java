package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;

@Data(staticConstructor = "of")
public class TimetableEntry {

  private final long buildingId;
  private final long roomId;
  @NonNull
  private final Class classInfo;
  @NonNull
  private final DayOfWeek dayOfWeek;
  @NonNull
  private final OpenTime classPeriod;

}
