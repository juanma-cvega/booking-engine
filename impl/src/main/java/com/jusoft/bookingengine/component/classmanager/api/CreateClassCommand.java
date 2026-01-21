package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.List;
import java.util.Objects;

public record CreateClassCommand(
        long buildingId, String description, String type, List<Long> instructorsId)
        implements Command {
    public CreateClassCommand {
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(type, "type must not be null");
        instructorsId = instructorsId == null ? List.of() : List.copyOf(instructorsId);
    }
}
