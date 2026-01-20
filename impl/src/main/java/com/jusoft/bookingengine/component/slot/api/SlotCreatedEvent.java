package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.Event;
import java.util.Objects;

public record SlotCreatedEvent(long slotId, long roomId, SlotState state, OpenDate openDate)
        implements Event {
    public SlotCreatedEvent {
        Objects.requireNonNull(state, "state must not be null");
        Objects.requireNonNull(openDate, "openDate must not be null");
    }
}
