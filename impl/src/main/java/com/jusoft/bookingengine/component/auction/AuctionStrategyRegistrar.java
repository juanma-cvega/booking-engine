package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerStrategyType;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
class AuctionStrategyRegistrar {

  private final Map<AuctionWinnerStrategyType, AuctionWinnerStrategy> strategies;

  AuctionWinnerStrategy findStrategyFor(AuctionWinnerStrategyType strategyType) {
    return strategies.get(strategyType);
  }
}
