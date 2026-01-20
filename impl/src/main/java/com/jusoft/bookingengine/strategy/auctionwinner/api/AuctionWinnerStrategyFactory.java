package com.jusoft.bookingengine.strategy.auctionwinner.api;

public interface AuctionWinnerStrategyFactory<T extends AuctionConfigInfo> {

    AuctionWinnerStrategy createInstance(T config);
}
