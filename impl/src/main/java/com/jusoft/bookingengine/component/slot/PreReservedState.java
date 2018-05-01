package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;

import java.time.Clock;

class PreReservedState implements SlotState {

  private static final PreReservedState INSTANCE = new PreReservedState();

  private PreReservedState() {
  }

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  @Override
  public SlotState preReserve(Slot slot, Clock clock) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  static SlotState getInstance() {
    return INSTANCE;
  }
}
