package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateInstructorTimetablesUseCase {

  private final InstructorTimetablesManagerComponent instructorTimetablesManagerComponent;

  public void createInstructorTimetables(long instructorId) {
    instructorTimetablesManagerComponent.registerInstructor(instructorId);
  }
}
