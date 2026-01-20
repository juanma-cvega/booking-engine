package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassListOfInstructorsCannotBeEmptyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import org.apache.commons.lang3.Validate;

@Data
class Class {

    private final long id;
    private final long buildingId;
    private final String description;
    private final List<Long> instructorsId;
    private final String classType;
    private final List<Long> roomsRegistered;

    Class(
            long id,
            long buildingId,
            String description,
            List<Long> instructorsId,
            String classType,
            List<Long> roomsRegistered) {
        Objects.requireNonNull(description);
        Objects.requireNonNull(classType);
        Objects.requireNonNull(roomsRegistered);
        Validate.notEmpty(instructorsId);
        this.id = id;
        this.buildingId = buildingId;
        this.description = description;
        this.instructorsId = new ArrayList<>(instructorsId);
        this.classType = classType;
        this.roomsRegistered = new ArrayList<>(roomsRegistered);
    }

    Class(
            long id,
            long buildingId,
            String description,
            List<Long> instructorsId,
            String classType) {
        this(id, buildingId, description, instructorsId, classType, new ArrayList<>());
    }

    Class addInstructor(long instructorId) {
        instructorsId.add(instructorId);
        return copy();
    }

    Class removeInstructor(long instructorId) {
        if (isLastInstructor(instructorId)) {
            throw new ClassListOfInstructorsCannotBeEmptyException(id, instructorId);
        }
        instructorsId.remove(instructorId);
        return copy();
    }

    private boolean isLastInstructor(long instructorId) {
        return instructorsId.contains(instructorId) && instructorsId.size() == 1;
    }

    Class registerRoom(long roomId) {
        roomsRegistered.add(roomId);
        return copy();
    }

    Class unregisterRoom(long roomId) {
        roomsRegistered.remove(roomId);
        return copy();
    }

    boolean canBeRemoved() {
        return roomsRegistered.isEmpty();
    }

    private Class copy() {
        return new Class(
                id,
                buildingId,
                description,
                new ArrayList<>(instructorsId),
                classType,
                new ArrayList<>(roomsRegistered));
    }

    List<Long> getRoomsRegistered() {
        return new ArrayList<>(roomsRegistered);
    }
}
