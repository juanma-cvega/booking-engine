package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class SlotAlreadyStartedException extends RuntimeException {

  private static final String MESSAGE = "Slot %s is past the start time";

  private final long slotId;

  public SlotAlreadyStartedException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
