package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;

class SlotEventFactory {

  SlotCreatedEvent slotCreatedEvent(Slot newSlot) {
    return new SlotCreatedEvent(
      newSlot.getId(),
      newSlot.getRoomId(),
      newSlot.getClubId(),
      newSlot.getStartDate(),
      newSlot.getEndDate());
  }
}
