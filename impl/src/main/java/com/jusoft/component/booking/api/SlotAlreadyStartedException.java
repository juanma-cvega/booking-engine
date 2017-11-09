package com.jusoft.component.booking.api;

import java.time.ZonedDateTime;

public class SlotAlreadyStartedException extends RuntimeException {

  private static final String MESSAGE = "Slot %s in room %s is past the start time at %s";

  public SlotAlreadyStartedException(long slotId, long roomId, ZonedDateTime startTime) {
    super(String.format(MESSAGE, slotId, roomId, startTime));
  }
}
