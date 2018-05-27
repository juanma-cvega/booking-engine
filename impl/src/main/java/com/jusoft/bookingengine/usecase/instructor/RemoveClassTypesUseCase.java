package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.RemoveClassTypesCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveClassTypesUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public void removeClassTypes(RemoveClassTypesCommand command) {
    instructorManagerComponent.removeClassTypes(command);
  }
}
