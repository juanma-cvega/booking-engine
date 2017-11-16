package com.jusoft.bookingengine.component.auction.api;

public class SlotNotInAuctionException extends RuntimeException {

  private static final String MESSAGE = "Auction not found for slot %s";

  public SlotNotInAuctionException(long slotId) {
    super(String.format(MESSAGE, slotId));
  }
}
