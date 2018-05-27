package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
public class RoomTimetable {

  @NonNull
  private final Map<DayOfWeek, List<OpenTime>> timetable;
}
