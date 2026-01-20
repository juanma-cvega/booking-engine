package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class SlotEventFactory {

    private final SlotStateFactory slotStateFactory;

    SlotCreatedEvent slotCreatedEvent(Slot newSlot) {
        return SlotCreatedEvent.of(
                newSlot.getId(),
                newSlot.getRoomId(),
                slotStateFactory.getSlotStateFor(newSlot.getState()),
                newSlot.getOpenDate());
    }
}
