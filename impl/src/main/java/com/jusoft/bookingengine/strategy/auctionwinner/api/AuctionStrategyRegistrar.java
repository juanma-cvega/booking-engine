package com.jusoft.bookingengine.strategy.auctionwinner.api;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class AuctionStrategyRegistrar {

  private final Map<Class<? extends AuctionConfigInfo>, AuctionWinnerStrategyFactory> factories;

  @SuppressWarnings("unchecked")
  public <T extends AuctionConfigInfo> AuctionWinnerStrategy createStrategyWith(T config) {
    return factories.get(config.getClass()).createInstance(config);
  }
}
