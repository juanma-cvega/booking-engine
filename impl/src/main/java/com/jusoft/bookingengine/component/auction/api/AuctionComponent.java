package com.jusoft.bookingengine.component.auction.api;

public interface AuctionComponent {

  AuctionView startAuction(CreateAuctionCommand createAuctionCommand);

  void addBuyerTo(long slotId, long userId);

  void finishAuction(FinishAuctionCommand finishAuctionCommand);

  boolean isAuctionOpenForSlot(long slotId);

}
