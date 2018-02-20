package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;

public interface AuctionManagerComponent {

  AuctionView startAuction(StartAuctionCommand startAuctionCommand);

  void addBidderTo(long slotId, long userId);

  void finishAuction(long auctionId, AuctionWinnerStrategy strategy);

  AuctionView find(long auctionId);
}
