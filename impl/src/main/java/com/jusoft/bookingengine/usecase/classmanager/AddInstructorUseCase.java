package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.AddInstructorCommand;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddInstructorUseCase {

  private final ClassManagerComponent classManagerComponent;

  public void addInstructor(AddInstructorCommand command) {
    classManagerComponent.addInstructor(command);
  }
}
