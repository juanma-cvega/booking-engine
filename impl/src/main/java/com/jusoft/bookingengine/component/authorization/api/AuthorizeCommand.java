package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import java.time.ZonedDateTime;
import java.util.Objects;

public record AuthorizeCommand(
        long userId, long roomId, long buildingId, long clubId, ZonedDateTime slotCreationTime)
        implements Command {
    public AuthorizeCommand {
        Objects.requireNonNull(slotCreationTime, "slotCreationTime must not be null");
    }
}
