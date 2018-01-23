package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class SlotAlreadyBookedException extends RuntimeException {
  private static final String MESSAGE = "Slot %s already taken";

  private final long slotId;

  public SlotAlreadyBookedException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
