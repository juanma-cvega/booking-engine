package com.jusoft.bookingengine.component.instructor.api;

import java.util.List;
import java.util.Optional;

public interface InstructorManagerComponent {

  InstructorView create(CreateInstructorCommand command);

  void registerOnBuilding(RegisterWithBuildingCommand command);

  void addClassTypes(AddClassTypeCommand command);

  Optional<InstructorView> find(long instructorId);

  List<InstructorView> findBy(SearchCriteriaCommand command);

  void remove(long instructorId);

  void unregisterFromBuilding(UnregisterFromBuildingCommand command);

  void removeClassTypes(RemoveClassTypesCommand command);

}
