package com.jusoft.bookingengine.component.auction.api.strategy;

public class NoAuctionConfigInfo implements AuctionConfigInfo {

  @Override
  public int getAuctionDuration() {
    return 0;
  }
}
