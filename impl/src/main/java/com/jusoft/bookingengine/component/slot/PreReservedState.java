package com.jusoft.bookingengine.component.slot;

class PreReservedState implements SlotState {

  private static final PreReservedState INSTANCE = new PreReservedState();

  private PreReservedState() {
  }

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  static SlotState getInstance() {
    return INSTANCE;
  }
}
