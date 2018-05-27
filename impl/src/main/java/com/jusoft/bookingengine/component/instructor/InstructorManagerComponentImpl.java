package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.AddClassTypeCommand;
import com.jusoft.bookingengine.component.instructor.api.CreateInstructorCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorCreatedEvent;
import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import com.jusoft.bookingengine.component.instructor.api.RegisterWithBuildingCommand;
import com.jusoft.bookingengine.component.instructor.api.RemoveClassTypesCommand;
import com.jusoft.bookingengine.component.instructor.api.SearchCriteriaCommand;
import com.jusoft.bookingengine.component.instructor.api.UnregisterFromBuildingCommand;
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
  public void registerOnBuilding(RegisterWithBuildingCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.registerOnBuilding(command));
  }

  @Override
  public void addClassTypes(AddClassTypeCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.addClassTypes(command.getClassTypes()));
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
    filter.and(instructor -> isInstructorInAnyBuilding(command, instructor));
    filter.and(instructor -> isSupportedClassTypesByInstructor(command, instructor));
    return filter;
  }

  private boolean isInstructorInAnyBuilding(SearchCriteriaCommand command, Instructor instructor) {
    return instructor.isRegisteredIn(command.getBuildingsId());
  }

  private boolean isSupportedClassTypesByInstructor(SearchCriteriaCommand command, Instructor instructor) {
    return instructor.getSupportedClassTypes().containsAll(command.getClassTypes());
  }

  @Override
  public void remove(long instructorId) {
    repository.delete(instructorId);
  }

  @Override
  public void unregisterFromBuilding(UnregisterFromBuildingCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.unregisterFromBuilding(command.getBuildingId()));
  }

  @Override
  public void removeClassTypes(RemoveClassTypesCommand command) {
    repository.execute(command.getInstructorId(), instructor -> instructor.unregisterClassTypes(command.getClassTypes()));
  }
}
