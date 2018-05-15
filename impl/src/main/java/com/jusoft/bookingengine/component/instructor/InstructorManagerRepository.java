package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

interface InstructorManagerRepository extends Repository {

  void save(Instructor newInstructor);

  void execute(long instructorId, UnaryOperator<Instructor> modifier);

  Optional<Instructor> find(long instructorId);

  List<Instructor> findBy(Predicate<Instructor> filters);

  void delete(long instructorId);
}
