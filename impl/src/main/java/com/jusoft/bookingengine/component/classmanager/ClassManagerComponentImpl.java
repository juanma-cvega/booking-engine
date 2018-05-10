package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.AddInstructorCommand;
import com.jusoft.bookingengine.component.classmanager.api.ClassCreatedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassInstructorAddedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassInstructorRemovedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassIsStillRegisteredInRoomsException;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassNotFoundException;
import com.jusoft.bookingengine.component.classmanager.api.ClassRemovedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.CreateClassCommand;
import com.jusoft.bookingengine.component.classmanager.api.RegisterRoomCommand;
import com.jusoft.bookingengine.component.classmanager.api.RemoveInstructorCommand;
import com.jusoft.bookingengine.component.classmanager.api.RoomRegisteredForClassEvent;
import com.jusoft.bookingengine.component.classmanager.api.RoomUnregisteredForClassEvent;
import com.jusoft.bookingengine.component.classmanager.api.UnregisterRoomCommand;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClassManagerComponentImpl implements ClassManagerComponent {

  private final ClassManagerComponentRepository repository;
  private final ClassFactory factory;
  private final MessagePublisher messagePublisher;

  @Override
  public ClassView create(CreateClassCommand command) {
    Class classCreated = factory.createFrom(command);
    repository.save(classCreated);
    messagePublisher.publish(ClassCreatedEvent.of(
      classCreated.getId(),
      classCreated.getDescription(),
      classCreated.getInstructorsId(),
      classCreated.getClassType()));
    return factory.createFrom(classCreated);
  }

  @Override
  public ClassView find(long classId) {
    return factory.createFrom(repository.find(classId).orElseThrow(() -> new ClassNotFoundException(classId)));
  }

  @Override
  public List<ClassView> findByBuilding(long buildingId) {
    return repository.findByBuildingId(buildingId).stream().map(factory::createFrom).collect(toList());
  }

  @Override
  public void remove(long classId) {
    boolean isRemoved = repository.removeIf(classId, Class::canBeRemoved);
    if (!isRemoved) {
      throw new ClassIsStillRegisteredInRoomsException(classId);
    }
    messagePublisher.publish(ClassRemovedEvent.of(classId));
  }

  @Override
  public void addInstructor(AddInstructorCommand command) {
    repository.execute(command.getClassId(), classFound -> classFound.addInstructor(command.getInstructorId()));
    messagePublisher.publish(ClassInstructorAddedEvent.of(command.getClassId(), command.getInstructorId()));
  }

  @Override
  public void removeInstructor(RemoveInstructorCommand command) {
    repository.execute(command.getClassId(), classFound -> classFound.removeInstructor(command.getInstructorId()));
    messagePublisher.publish(ClassInstructorRemovedEvent.of(command.getClassId(), command.getInstructorId()));
  }

  @Override
  public void registerRoom(RegisterRoomCommand command) {
    repository.execute(command.getClassId(), classFound -> classFound.registerRoom(command.getRoomId()));
    messagePublisher.publish(RoomRegisteredForClassEvent.of(command.getClassId(), command.getRoomId()));
  }

  @Override
  public void unregisterRoom(UnregisterRoomCommand command) {
    repository.execute(command.getClassId(), classFound -> classFound.unregisterRoom(command.getRoomId()));
    messagePublisher.publish(RoomUnregisteredForClassEvent.of(command.getClassId(), command.getRoomId()));
  }
}
