package com.jusoft.bookingengine.component.auction.api;

public class AuctionNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Auction %s not found";

  public AuctionNotFoundException(long auctionId) {
    super(String.format(MESSAGE, auctionId));
  }
}
