package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteInstructorTimetablesUseCase {

  private final InstructorTimetablesManagerComponent instructorTimetablesManagerComponent;

  public void unregisterInstructor(long instructorId) {
    instructorTimetablesManagerComponent.unregisterInstructor(instructorId);
  }
}
