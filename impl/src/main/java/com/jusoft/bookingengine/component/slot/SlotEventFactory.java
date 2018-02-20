package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotMadeAvailableEvent;
import com.jusoft.bookingengine.component.slot.api.SlotReservedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;

class SlotEventFactory {

  SlotCreatedEvent slotCreatedEvent(Slot newSlot) {
    return SlotCreatedEvent.of(
      newSlot.getId(),
      newSlot.getRoomId(),
      SlotState.valueOf(newSlot.getState().name()),
      newSlot.getOpenDate());
  }

  SlotReservedEvent slotReservedEvent(Slot slot, ReserveSlotCommand command) {
    return SlotReservedEvent.of(slot.getId(), command.getUserId());
  }

  public SlotMadeAvailableEvent slotMadeAvailableEvent(Slot slot) {
    return SlotMadeAvailableEvent.of(slot.getId());
  }
}
