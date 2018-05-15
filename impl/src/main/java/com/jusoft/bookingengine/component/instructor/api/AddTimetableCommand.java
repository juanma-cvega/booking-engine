package com.jusoft.bookingengine.component.instructor.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class AddTimetableCommand implements Command {

  private final long instructorId;
  private final long buildingId;
  private final long roomId;
  @NonNull
  private final Timetable timetable;
}
