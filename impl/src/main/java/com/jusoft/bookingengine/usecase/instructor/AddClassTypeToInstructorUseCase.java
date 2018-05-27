package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.AddClassTypeCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddClassTypeToInstructorUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public void addClassType(AddClassTypeCommand command) {
    instructorManagerComponent.addClassTypes(command);
  }
}
