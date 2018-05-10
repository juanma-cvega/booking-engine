package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.RemoveInstructorCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveInstructorUseCase {

  private final ClassManagerComponent classManagerComponent;

  public void removeInstructor(RemoveInstructorCommand command) {
    classManagerComponent.removeInstructor(command);
  }
}
