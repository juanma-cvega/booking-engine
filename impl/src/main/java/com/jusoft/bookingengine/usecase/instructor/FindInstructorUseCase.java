package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorNotFoundException;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindInstructorUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public InstructorView findInstructor(long instructorId) {
    return instructorManagerComponent.find(instructorId).orElseThrow(() -> new InstructorNotFoundException(instructorId));
  }
}
