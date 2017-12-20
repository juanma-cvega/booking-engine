package com.jusoft.bookingengine.component.auction.api;

import java.util.Optional;

public interface AuctionComponent {

  AuctionView startAuction(CreateAuctionCommand createAuctionCommand);

  void addBuyerTo(long slotId, long userId);

  void finishAuction(FinishAuctionCommand finishAuctionCommand);

  boolean isAuctionOpenForSlot(long slotId);

  Optional<AuctionView> find(long auctionId);
}
