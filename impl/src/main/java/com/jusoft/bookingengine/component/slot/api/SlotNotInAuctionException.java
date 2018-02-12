package com.jusoft.bookingengine.component.slot.api;

public class SlotNotInAuctionException extends RuntimeException {

  private static final String MESSAGE = "Slot %s is not part of an open auction";

  private final long slotId;

  public SlotNotInAuctionException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
