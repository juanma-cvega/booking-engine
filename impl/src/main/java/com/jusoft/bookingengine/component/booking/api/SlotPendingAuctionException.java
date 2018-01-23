package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class SlotPendingAuctionException extends RuntimeException {

  private static final String MESSAGE = "The slot %s is currently in auction";

  private final long slotId;

  public SlotPendingAuctionException(long slotId) {
    super(String.format(MESSAGE, slotId));
    this.slotId = slotId;
  }
}
