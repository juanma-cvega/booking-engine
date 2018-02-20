package com.jusoft.bookingengine.component.auction.api;

public interface AuctionComponent {

  AuctionView startAuction(StartAuctionCommand createAuctionCommand);

  void addBidderTo(long slotId, long userId);

  void finishAuction(FinishAuctionCommand finishAuctionCommand);

  AuctionView find(long auctionId);
}
