package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Slot %s not found.";

  private final long slotId;

  public SlotNotFoundException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
