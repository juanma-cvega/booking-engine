package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.Command;
import java.util.Objects;

public record CreateSlotCommand(long roomId, long buildingId, long clubId, OpenDate openDate)
        implements Command {
    public CreateSlotCommand {
        Objects.requireNonNull(openDate, "openDate must not be null");
    }
}
