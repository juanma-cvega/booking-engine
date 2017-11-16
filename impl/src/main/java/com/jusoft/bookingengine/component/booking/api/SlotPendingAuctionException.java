package com.jusoft.bookingengine.component.booking.api;

public class SlotPendingAuctionException extends RuntimeException {

  private static final String MESSAGE = "The slot %s of room %s is currently in auction";

  public SlotPendingAuctionException(long slotId, long roomId) {
    super(String.format(MESSAGE, slotId, roomId));
  }
}
