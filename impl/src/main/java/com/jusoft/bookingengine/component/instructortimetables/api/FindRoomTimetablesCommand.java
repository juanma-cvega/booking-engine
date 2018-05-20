package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class FindRoomTimetablesCommand implements Command {

  private final long instructorId;
  private final long roomId;
}
