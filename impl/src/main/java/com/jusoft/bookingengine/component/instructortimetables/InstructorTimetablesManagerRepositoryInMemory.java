package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesNotFoundException;
import com.jusoft.bookingengine.repository.AbstractRepositoryInMemory;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.UnaryOperator;

class InstructorTimetablesManagerRepositoryInMemory extends AbstractRepositoryInMemory<Long, InstructorTimetables> implements InstructorTimetablesManagerRepository {

  InstructorTimetablesManagerRepositoryInMemory(ConcurrentMap<Long, InstructorTimetables> store) {
    super(store);
  }

  @Override
  public void save(InstructorTimetables instructorTimetables) {
    super.save(instructorTimetables);
  }

  @Override
  public void delete(long instructorId) {
    super.deleteIf(instructorId, instructorTimetables -> true);
  }

  @Override
  public Optional<InstructorTimetables> find(long instructorId) {
    return super.find(instructorId);
  }

  @Override
  public void execute(long instructorId, UnaryOperator<InstructorTimetables> modifier) {
    super.execute(instructorId, modifier);
  }

  @Override
  protected Long getIdFrom(InstructorTimetables entity) {
    return entity.getInstructorId();
  }

  @Override
  protected RuntimeException createNotFoundException(Long entityId) {
    return new InstructorTimetablesNotFoundException(entityId);
  }
}
