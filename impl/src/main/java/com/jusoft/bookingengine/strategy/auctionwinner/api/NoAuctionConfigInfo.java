package com.jusoft.bookingengine.strategy.auctionwinner.api;

public class NoAuctionConfigInfo implements AuctionConfigInfo {

  @Override
  public int getAuctionDuration() {
    return 0;
  }
}
