package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.SearchCriteriaCommand;
import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;
import java.util.function.UnaryOperator;

interface InstructorTimetablesManagerRepository extends Repository {

  void save(InstructorTimetables instructorTimetables);

  void delete(long instructorId);

  Optional<InstructorTimetables> find(long instructorId);

  void execute(long instructorId, UnaryOperator<InstructorTimetables> modifier);

  InstructorTimetables findByInstructorAndBuilding(long instructorId, long buildingId);

  InstructorTimetables findByInstructorAndRoom(long instructorId, long roomId);

  Optional<InstructorTimetablesView> findBy(SearchCriteriaCommand criteria);
}
