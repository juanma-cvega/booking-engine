package com.jusoft.component.slot;

import com.jusoft.component.slot.api.SlotCreatedEvent;

class SlotEventFactory {

  SlotCreatedEvent slotCreatedEvent(Slot newSlot) {
    return new SlotCreatedEvent(
      newSlot.getId(),
      newSlot.getRoomId(),
      newSlot.getStartDate(),
      newSlot.getEndDate());
  }
}
