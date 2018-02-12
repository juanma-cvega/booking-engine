package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class SlotAlreadyReservedException extends RuntimeException {
  private static final String MESSAGE = "Slot %s already taken";

  private final long slotId;

  public SlotAlreadyReservedException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
