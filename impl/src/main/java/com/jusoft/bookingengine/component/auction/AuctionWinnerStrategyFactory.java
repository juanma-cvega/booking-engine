package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;

public interface AuctionWinnerStrategyFactory<T extends AuctionConfigInfo> {

  AuctionWinnerStrategy createInstance(T config);
}
