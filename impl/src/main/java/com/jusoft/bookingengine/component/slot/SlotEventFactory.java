package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;

class SlotEventFactory {

  SlotCreatedEvent slotCreatedEvent(Slot newSlot) {
    return SlotCreatedEvent.of(
      newSlot.getId(),
      newSlot.getRoomId(),
      SlotStateFactory.getSlotStateFor(newSlot.getState()),
      newSlot.getOpenDate());
  }
}
