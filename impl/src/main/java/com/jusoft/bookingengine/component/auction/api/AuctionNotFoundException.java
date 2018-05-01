package com.jusoft.bookingengine.component.auction.api;

import lombok.Getter;

@Getter
public class AuctionNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 3727164628501370119L;

  private static final String MESSAGE = "Auction %s not found";

  private final long auctionId;

  public AuctionNotFoundException(long auctionId) {
    super(String.format(MESSAGE, auctionId));
    this.auctionId = auctionId;
  }
}
