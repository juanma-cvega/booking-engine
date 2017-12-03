package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.auction.Auction;

import java.util.Optional;

public interface AuctionComponent {

  Auction startAuction(CreateAuctionCommand createAuctionCommand);

  void addBuyerTo(long slotId, long userId);

  Optional<Auction> findBySlot(long slotId);

  void finishAuction(FinishAuctionCommand finishAuctionCommand);

  boolean isAuctionOpenForSlot(long slotId);
}
