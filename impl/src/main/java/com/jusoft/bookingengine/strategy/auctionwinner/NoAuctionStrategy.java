package com.jusoft.bookingengine.strategy.auctionwinner;

import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;

import java.util.Optional;
import java.util.Set;

class NoAuctionStrategy implements AuctionWinnerStrategy {
  @Override
  public Optional<Long> findWinner(Set<Bid> bidders) {
    return Optional.empty();
  }
}
