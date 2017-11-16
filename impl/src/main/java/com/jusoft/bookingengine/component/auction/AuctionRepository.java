package com.jusoft.bookingengine.component.auction;

import java.util.Optional;

public interface AuctionRepository {

  void save(Auction newAuction);

  Optional<Auction> findOneBySlot(long slotId);

  Optional<Auction> find(long auctionId);
}
