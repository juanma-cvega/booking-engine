package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import com.jusoft.bookingengine.component.instructortimetables.api.RemoveTimetableEntriesCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveTimetableFromInstructorUseCase {

  private final InstructorTimetablesManagerComponent instructorTimetablesManagerComponent;

  public void removeTimetable(RemoveTimetableEntriesCommand command) {
    instructorTimetablesManagerComponent.removeTimetable(command);
  }
}
