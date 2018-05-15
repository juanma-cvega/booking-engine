package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorNotFoundException;
import com.jusoft.bookingengine.repository.AbstractRepositoryInMemory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.toList;

class InstructorManagerRepositoryInMemory extends AbstractRepositoryInMemory<Long, Instructor> implements InstructorManagerRepository {

  InstructorManagerRepositoryInMemory(ConcurrentMap<Long, Instructor> store) {
    super(store);
  }

  @Override
  public void save(Instructor newInstructor) {
    super.save(newInstructor);
  }

  @Override
  public void execute(long instructorId, UnaryOperator<Instructor> modifier) {
    super.execute(instructorId, modifier);
  }

  @Override
  public Optional<Instructor> find(long instructorId) {
    return super.find(instructorId);
  }

  @Override
  public List<Instructor> findBy(Predicate<Instructor> filters) {
    return store.values().stream()
      .filter(filters)
      .collect(toList());
  }

  @Override
  public void delete(long instructorId) {
    super.deleteIf(instructorId, instructor -> true);
  }

  @Override
  protected Long getIdFrom(Instructor entity) {
    return entity.getId();
  }

  @Override
  protected InstructorNotFoundException createNotFoundException(Long instructorId) {
    return new InstructorNotFoundException(instructorId);
  }
}
