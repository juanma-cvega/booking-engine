package com.jusoft.bookingengine.strategy.auctionwinner.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NoAuctionConfigInfo implements AuctionConfigInfo {

  private static final NoAuctionConfigInfo INSTANCE = new NoAuctionConfigInfo();

  @Override
  public int getAuctionDuration() {
    return 0;
  }

  public static NoAuctionConfigInfo getInstance() {
    return INSTANCE;
  }
}
