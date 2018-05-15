package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;

import java.util.Map;

@Data(staticConstructor = "of")
public class RoomTimetableDetails {

  private final long roomId;
  private final Map<String, Timetable> timetables;
}
