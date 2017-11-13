package com.jusoft.bookingengine.component.booking.api;

public class SlotAlreadyBookedException extends RuntimeException {
  private static final String MESSAGE = "Slot %s in room %s already taken";

  public SlotAlreadyBookedException(long roomId, long slotId) {
    super(String.format(MESSAGE, roomId, slotId));
  }
}
