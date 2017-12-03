package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionStrategyRegistrar {

  private final Map<Class<? extends AuctionConfigInfo>, AuctionWinnerStrategyFactory> factories;

  @SuppressWarnings("unchecked")
  <T extends AuctionConfigInfo> AuctionWinnerStrategy createStrategyWith(T config) {
    return factories.get(config.getClass()).createInstance(config);
  }
}
