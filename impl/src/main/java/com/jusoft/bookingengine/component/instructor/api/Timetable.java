package com.jusoft.bookingengine.component.instructor.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;

@Data(staticConstructor = "of")
public class Timetable {

  @NonNull
  private final String classType;
  @NonNull
  private final EnumMap<DayOfWeek, List<OpenTime>> daysTimetable;

  public EnumMap<DayOfWeek, List<OpenTime>> getDaysTimetable() {
    return new EnumMap<>(daysTimetable);
  }
}
