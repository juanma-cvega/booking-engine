package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesNotFoundException;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindInstructorTimetablesUseCase {

  private final InstructorTimetablesManagerComponent instructorTimetablesManagerComponent;

  public InstructorTimetablesView find(long instructorId) {
    return instructorTimetablesManagerComponent.find(instructorId)
      .orElseThrow(() -> new InstructorTimetablesNotFoundException(instructorId));
  }
}
