package com.jusoft.bookingengine.component.auction.api;

import lombok.Getter;

@Getter
public class AuctionFinishedException extends RuntimeException {

  private static final long serialVersionUID = -681758047185010995L;

  private static final String MESSAGE = "Auction %s for slot %s is already finished";

  private final long auctionId;
  private final long slotId;

  public AuctionFinishedException(long auctionId, long slotId) {
    super(String.format(MESSAGE, auctionId, slotId));
    this.auctionId = auctionId;
    this.slotId = slotId;
  }
}
