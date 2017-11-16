package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionConfig;

import java.util.Set;

public interface AuctionWinnerStrategy<T extends AuctionConfig> {

  long findWinner(Set<Bid> buyers, T auctionConfig);
}
