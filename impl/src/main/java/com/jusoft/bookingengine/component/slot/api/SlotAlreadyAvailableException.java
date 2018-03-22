package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotAlreadyAvailableException extends RuntimeException {

  private static final String MESSAGE = "Slot %s already made available";
  private final long slotId;

  public SlotAlreadyAvailableException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
