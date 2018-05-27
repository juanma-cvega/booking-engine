package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;
import java.util.function.UnaryOperator;

interface InstructorTimetablesManagerRepository extends Repository {

  void save(InstructorTimetables instructorTimetables);

  void delete(long instructorId);

  Optional<InstructorTimetables> find(long instructorId);

  void execute(long instructorId, UnaryOperator<InstructorTimetables> modifier);

}
