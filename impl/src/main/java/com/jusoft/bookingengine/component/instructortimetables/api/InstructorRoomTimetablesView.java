package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.component.instructor.api.Timetable;
import lombok.Data;

import java.util.Map;

@Data(staticConstructor = "of")
public class InstructorRoomTimetablesView {

  private final long instructorId;
  private final long roomId;
  private final Map<String, Timetable> timetables;
}
