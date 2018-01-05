package com.jusoft.bookingengine.strategy.auctionwinner;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategyFactory;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;

public class NoAuctionStrategyFactory implements AuctionWinnerStrategyFactory<NoAuctionConfigInfo> {
  @Override
  public AuctionWinnerStrategy createInstance(NoAuctionConfigInfo config) {
    return new NoAuctionStrategy();
  }
}
