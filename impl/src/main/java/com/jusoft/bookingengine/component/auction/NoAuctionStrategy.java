package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.Bid;

import java.util.Optional;
import java.util.Set;

public class NoAuctionStrategy implements AuctionWinnerStrategy {
  @Override
  public Optional<Long> findWinner(Set<Bid> buyers) {
    return Optional.empty();
  }
}
