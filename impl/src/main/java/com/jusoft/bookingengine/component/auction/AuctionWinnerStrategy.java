package com.jusoft.bookingengine.component.auction;

import java.util.Optional;
import java.util.Set;

public interface AuctionWinnerStrategy {

  Optional<Long> findWinner(Set<Bid> buyers);
}
