package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.auction.Auction;

public interface AuctionComponent {

  void startAuction(long slotId, long roomId);

  void addBuyerTo(long slotId, long userId);

  Auction findBySlot(long slotId);

  void finishAuction(long auctionId);

  boolean isAuctionOpenForSlot(long slotId);
}
