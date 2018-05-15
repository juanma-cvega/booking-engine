package com.jusoft.bookingengine.component.instructor.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class RemoveFromBuildingCommand implements Command {

  private final long instructorId;
  private final long buildingId;
}
