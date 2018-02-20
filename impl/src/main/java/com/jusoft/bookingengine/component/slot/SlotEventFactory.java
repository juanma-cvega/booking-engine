package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;

class SlotEventFactory {

  SlotCreatedEvent slotCreatedEvent(Slot newSlot) {
    return SlotCreatedEvent.of(
      newSlot.getId(),
      newSlot.getRoomId(),
      SlotState.valueOf(newSlot.getState().name()),
      newSlot.getOpenDate());
  }
}
