package com.jusoft.bookingengine.component.slot;

class ReservedState implements SlotState {

  private static final ReservedState INSTANCE = new ReservedState();

  private ReservedState() {
  }

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  static SlotState getInstance() {
    return INSTANCE;
  }
}
