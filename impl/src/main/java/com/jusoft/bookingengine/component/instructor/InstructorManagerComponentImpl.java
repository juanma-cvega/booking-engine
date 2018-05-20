package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.AddClassTypeCommand;
import com.jusoft.bookingengine.component.instructor.api.AddTimetableCommand;
import com.jusoft.bookingengine.component.instructor.api.AddToBuildingCommand;
import com.jusoft.bookingengine.component.instructor.api.CreateInstructorCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorCreatedEvent;
import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import com.jusoft.bookingengine.component.instructor.api.RemoveClassTypesCommand;
import com.jusoft.bookingengine.component.instructor.api.RemoveFromBuildingCommand;
import com.jusoft.bookingengine.component.instructor.api.RemoveTimetableCommand;
import com.jusoft.bookingengine.component.instructor.api.SearchCriteriaCommand;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
class InstructorManagerComponentImpl implements InstructorManagerComponent {

  private final InstructorManagerRepository repository;
  private final InstructorFactory factory;
  private final MessagePublisher messagePublisher;

  @Override
  public InstructorView create(CreateInstructorCommand command) {
    Instructor newInstructor = factory.createFrom(command);
    repository.save(newInstructor);
    messagePublisher.publish(InstructorCreatedEvent.of(newInstructor.getId(), newInstructor.getClubId()));
    return factory.createFrom(newInstructor);
  }

  @Override
  public void addToBuilding(AddToBuildingCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.addToBuilding(command.getBuildingId()));
  }

  @Override
  public void addClassType(AddClassTypeCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.addClassType(command.getClassType()));
  }

  @Override
  public Optional<InstructorView> find(long instructorId) {
    return repository.find(instructorId).map(factory::createFrom);
  }

  @Override
  public List<InstructorView> findBy(SearchCriteriaCommand command) {
    return repository.findBy(createFiltersFrom(command)).stream().map(factory::createFrom).collect(toList());
  }

  private Predicate<Instructor> createFiltersFrom(SearchCriteriaCommand command) {
    Predicate<Instructor> filter = instructor -> true;
    filter.and(instructor -> isInstructorInBuildingOrTrue(command, instructor));
    filter.and(instructor -> isSupportedClassTypesByInstructor(command, instructor));
    return filter;
  }

  private boolean isInstructorInBuildingOrTrue(SearchCriteriaCommand command, Instructor instructor) {
    return command.getBuildingId()
      .map(instructor::isRegisteredIn)
      .orElse(true);
  }

  private boolean isSupportedClassTypesByInstructor(SearchCriteriaCommand command, Instructor instructor) {
    return instructor.getSupportedClassTypes().containsAll(command.getClassTypes());
  }

  @Override
  public void delete(long instructorId) {
    repository.delete(instructorId);
  }

  @Override
  public void unregisterFromBuilding(RemoveFromBuildingCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.unregisterFromBuilding(command.getBuildingId()));
  }

  @Override
  public void unregisterClassTypes(RemoveClassTypesCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.unregisterClassTypes(command.getClassTypes()));
  }

  @Override
  public void addTimetable(AddTimetableCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.addTimetable(command));
  }

  @Override
  public void removeTimetable(RemoveTimetableCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.removeTimetable(command));
  }
}
