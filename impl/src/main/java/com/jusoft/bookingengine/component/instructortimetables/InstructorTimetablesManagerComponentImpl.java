package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.AddTimetableCommand;
import com.jusoft.bookingengine.component.instructor.api.RemoveTimetableCommand;
import com.jusoft.bookingengine.component.instructortimetables.api.FindBuildingTimetablesCommand;
import com.jusoft.bookingengine.component.instructortimetables.api.FindRoomTimetablesCommand;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorBuildingTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorRoomTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.SearchCriteriaCommand;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
class InstructorTimetablesManagerComponentImpl implements InstructorTimetablesManagerComponent {

  private final InstructorTimetablesManagerRepository repository;
  private final InstructorTimetablesFactory factory;

  @Override
  public InstructorTimetablesView registerInstructor(long instructorId) {
    InstructorTimetables newInstructorTimetables = new InstructorTimetables(instructorId);
    repository.save(newInstructorTimetables);
    return factory.createFrom(newInstructorTimetables);
  }

  @Override
  public void unregisterInstructor(long instructorId) {
    repository.delete(instructorId);
  }

  @Override
  public Optional<InstructorTimetablesView> getInstructor(long instructorId) {
    return repository.find(instructorId).map(factory::createFrom);
  }

  @Override
  public void addTimetable(AddTimetableCommand command) {
    repository.execute(command.getInstructorId(), instructorTimetables -> instructorTimetables.addTimetable(command));
  }

  @Override
  public void removeTimetable(RemoveTimetableCommand command) {
    repository.execute(command.getInstructorId(), instructorTimetables -> instructorTimetables.removeTimetable(command));
  }

  @Override
  public InstructorTimetablesView findBy(SearchCriteriaCommand criteria) {
    return repository.findBy(criteria);
  }

  @Override
  public InstructorBuildingTimetablesView getTimetablesFor(FindBuildingTimetablesCommand command) {
    InstructorTimetables instructorTimetables = repository.findByInstructorAndBuilding(command.getInstructorId(), command.getBuildingId());
    return factory.createInstructorBuildingTimetablesViewFrom(instructorTimetables, command.getBuildingId());
  }

  @Override
  public InstructorRoomTimetablesView getTimetablesFor(FindRoomTimetablesCommand command) {
    InstructorTimetables instructorTimetables = repository.findByInstructorAndRoom(command.getInstructorId(), command.getRoomId());
    return factory.createInstructorRoomTimetablesViewFrom(instructorTimetables, command.getRoomId());
  }
}
