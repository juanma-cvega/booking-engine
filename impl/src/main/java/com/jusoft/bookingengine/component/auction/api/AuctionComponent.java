package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.auction.Auction;

public interface AuctionComponent {

  Auction startAuction(CreateAuctionCommand createAuctionCommand);

  void addBuyerTo(long slotId, long userId);

  void finishAuction(FinishAuctionCommand finishAuctionCommand);

  boolean isAuctionOpenForSlot(long slotId);

}
