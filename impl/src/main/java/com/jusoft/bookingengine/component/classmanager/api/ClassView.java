package com.jusoft.bookingengine.component.classmanager.api;

import java.util.List;
import java.util.Objects;

public record ClassView(
        long id,
        long buildingId,
        String description,
        List<Long> instructorsId,
        String classType,
        List<Long> roomsRegistered) {
    public ClassView {
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(instructorsId, "instructorsId must not be null");
        Objects.requireNonNull(classType, "classType must not be null");
        Objects.requireNonNull(roomsRegistered, "roomsRegistered must not be null");
        instructorsId = List.copyOf(instructorsId);
        roomsRegistered = List.copyOf(roomsRegistered);
    }
}
