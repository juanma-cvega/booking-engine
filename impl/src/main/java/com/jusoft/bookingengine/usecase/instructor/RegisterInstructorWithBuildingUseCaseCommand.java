package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class RegisterInstructorWithBuildingUseCaseCommand implements Command {

  private final long buildingId;
  private final long instructorId;
}
