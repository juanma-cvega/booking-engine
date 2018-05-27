package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveInstructorUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public void removeInstructor(long instructorId) {
    instructorManagerComponent.remove(instructorId);
  }
}
