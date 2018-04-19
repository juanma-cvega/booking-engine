package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotAlreadyReservedException extends RuntimeException {

  private static final long serialVersionUID = 2884146960146660578L;

  private static final String MESSAGE = "Slot %s already reserved";

  private final long slotId;

  public SlotAlreadyReservedException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
