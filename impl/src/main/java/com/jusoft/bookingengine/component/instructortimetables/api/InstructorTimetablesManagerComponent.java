package com.jusoft.bookingengine.component.instructortimetables.api;

import java.util.Optional;

public interface InstructorTimetablesManagerComponent {

  void registerInstructor(long instructorId);

  void unregisterInstructor(long instructorId);

  Optional<InstructorTimetablesView> find(long instructorId);

  void addTimetable(AddTimetableEntriesCommand command);

  void removeTimetable(RemoveTimetableEntriesCommand command);

}
