package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotAlreadyReservedException extends RuntimeException {

  private static final long serialVersionUID = 2884146960146660578L;

  private static final String MESSAGE = "Slot %s is already reserved by user %s, user %s cannot reserve it";

  private final long slotId;
  private final transient SlotUser previousUser;
  private final transient SlotUser newUser;

  public SlotAlreadyReservedException(long slotId, SlotUser previousUser, SlotUser newUser) {
    super(String.format(MESSAGE, slotId, previousUser, newUser));
    this.slotId = slotId;
    this.previousUser = previousUser;
    this.newUser = newUser;
  }
}
