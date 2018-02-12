package com.jusoft.bookingengine.component.auction.api;

import java.util.Optional;

public interface AuctionComponent {

  AuctionView startAuction(StartAuctionCommand createAuctionCommand);

  void addBidderTo(long slotId, long userId);

  void finishAuction(FinishAuctionCommand finishAuctionCommand);

  Optional<AuctionView> find(long auctionId);
}
