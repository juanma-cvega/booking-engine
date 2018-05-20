package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.component.instructor.api.AddTimetableCommand;
import com.jusoft.bookingengine.component.instructor.api.RemoveTimetableCommand;

import java.util.Optional;

public interface InstructorTimetablesManagerComponent {

  InstructorTimetablesView registerInstructor(long instructorId);

  void unregisterInstructor(long instructorId);

  Optional<InstructorTimetablesView> getInstructor(long instructorId);

  void addTimetable(AddTimetableCommand command);

  void removeTimetable(RemoveTimetableCommand command);

  InstructorTimetablesView findBy(SearchCriteriaCommand command);

  InstructorBuildingTimetablesView getTimetablesFor(FindBuildingTimetablesCommand command);

  InstructorRoomTimetablesView getTimetablesFor(FindRoomTimetablesCommand command);
}
