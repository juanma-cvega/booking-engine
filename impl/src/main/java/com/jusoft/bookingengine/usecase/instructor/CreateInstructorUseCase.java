package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.CreateInstructorCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateInstructorUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public InstructorView createInstructor(CreateInstructorCommand command) {
    return instructorManagerComponent.create(command);
  }
}
