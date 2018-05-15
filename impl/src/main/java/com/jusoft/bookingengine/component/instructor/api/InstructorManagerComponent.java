package com.jusoft.bookingengine.component.instructor.api;

import java.util.List;
import java.util.Optional;

public interface InstructorManagerComponent {

  InstructorView create(CreateInstructorCommand command);

  void addToBuilding(AddToBuildingCommand command);

  void addClassType(AddClassTypeCommand command);

  Optional<InstructorView> find(long instructorId);

  List<InstructorView> findBy(SearchCriteriaCommand command);

  void delete(long instructorId);

  void unregisterFromBuilding(RemoveFromBuildingCommand command);

  void unregisterClassType(RemoveClassTypesCommand command);

  void addTimetable(AddTimetableCommand command);

  void removeTimetable(RemoveTimetableCommand command);
}
