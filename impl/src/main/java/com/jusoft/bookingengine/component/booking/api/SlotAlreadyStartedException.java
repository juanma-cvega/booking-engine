package com.jusoft.bookingengine.component.booking.api;

public class SlotAlreadyStartedException extends RuntimeException {

  private static final String MESSAGE = "Slot %s in room %s is past the start time";

  public SlotAlreadyStartedException(long slotId, long roomId) {
    super(String.format(MESSAGE, slotId, roomId));
  }
}
