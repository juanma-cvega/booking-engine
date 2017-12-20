package com.jusoft.bookingengine.component.auction.api;

import lombok.Getter;

@Getter
public class AuctionFinishedException extends RuntimeException {

  private static final String MESSAGE = "Auction %s for slot %s in room %s is already finished";

  private final long auctionId;
  private final long slotId;
  private final long roomId;

  public AuctionFinishedException(long auctionId, long slotId, long roomId) {
    super(String.format(MESSAGE, auctionId, slotId, roomId));
    this.auctionId = auctionId;
    this.slotId = slotId;
    this.roomId = roomId;
  }
}
