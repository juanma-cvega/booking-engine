package com.jusoft.bookingengine.component.classmanager;

import static java.util.stream.Collectors.toList;

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
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClassManagerComponentImpl implements ClassManagerComponent {

    private final ClassManagerComponentRepository repository;
    private final ClassFactory factory;
    private final MessagePublisher messagePublisher;

    @Override
    public ClassView create(CreateClassCommand command) {
        Class classCreated = factory.createFrom(command);
        repository.save(classCreated);
        messagePublisher.publish(
                new ClassCreatedEvent(
                        classCreated.getId(),
                        classCreated.getDescription(),
                        classCreated.getInstructorsId(),
                        classCreated.getClassType()));
        return factory.createFrom(classCreated);
    }

    @Override
    public ClassView find(long classId) {
        return factory.createFrom(
                repository.find(classId).orElseThrow(() -> new ClassNotFoundException(classId)));
    }

    @Override
    public List<ClassView> findByBuilding(long buildingId) {
        return repository.findByBuildingId(buildingId).stream()
                .map(factory::createFrom)
                .collect(toList());
    }

    @Override
    public void remove(long classId) {
        boolean isRemoved = repository.removeIf(classId, Class::canBeRemoved);
        if (!isRemoved) {
            throw new ClassIsStillRegisteredInRoomsException(classId);
        }
        messagePublisher.publish(new ClassRemovedEvent(classId));
    }

    @Override
    public void addInstructor(AddInstructorCommand command) {
        repository.execute(
                command.classId(), classFound -> classFound.addInstructor(command.instructorId()));
        messagePublisher.publish(
                new ClassInstructorAddedEvent(command.classId(), command.instructorId()));
    }

    @Override
    public void removeInstructor(RemoveInstructorCommand command) {
        repository.execute(
                command.classId(),
                classFound -> classFound.removeInstructor(command.instructorId()));
        messagePublisher.publish(
                new ClassInstructorRemovedEvent(command.classId(), command.instructorId()));
    }

    @Override
    public void registerRoom(RegisterRoomCommand command) {
        repository.execute(
                command.classId(), classFound -> classFound.registerRoom(command.roomId()));
        messagePublisher.publish(
                new RoomRegisteredForClassEvent(command.classId(), command.roomId()));
    }

    @Override
    public void unregisterRoom(UnregisterRoomCommand command) {
        repository.execute(
                command.classId(), classFound -> classFound.unregisterRoom(command.roomId()));
        messagePublisher.publish(
                new RoomUnregisteredForClassEvent(command.classId(), command.roomId()));
    }
}
