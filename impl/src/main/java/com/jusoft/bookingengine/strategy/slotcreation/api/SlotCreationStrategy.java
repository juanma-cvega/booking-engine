package com.jusoft.bookingengine.strategy.slotcreation.api;

import java.time.ZonedDateTime;

public interface SlotCreationStrategy {

    ZonedDateTime nextSlotCreationTimeFor(long roomId);
}
