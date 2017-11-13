package com.jusoft.bookingengine.component.slot.api;

public class SlotNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Slot %s not found in room %s";

  public SlotNotFoundException(long slotId, long roomId) {
    super(String.format(MESSAGE, slotId, roomId));
  }
}
