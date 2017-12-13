package com.jusoft.bookingengine.component.auction;


import com.jusoft.bookingengine.component.auction.api.Bid;

import java.util.Optional;
import java.util.Set;

public interface AuctionWinnerStrategy {

  Optional<Long> findWinner(Set<Bid> buyers);
}
