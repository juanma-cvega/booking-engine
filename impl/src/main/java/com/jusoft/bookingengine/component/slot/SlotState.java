package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;

import java.time.Clock;

interface SlotState {

  SlotState makeAvailable(Slot slot);

  default SlotState reserve(Slot slot, Clock clock) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  default SlotState preReserve(Slot slot, Clock clock) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

}
