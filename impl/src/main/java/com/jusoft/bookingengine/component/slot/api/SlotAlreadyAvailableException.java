package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotAlreadyAvailableException extends RuntimeException {

  private static final long serialVersionUID = 8063953804783487851L;

  private static final String MESSAGE = "Slot %s already made available";

  private final long slotId;

  public SlotAlreadyAvailableException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
