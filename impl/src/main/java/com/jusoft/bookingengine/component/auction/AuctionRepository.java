package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;

interface AuctionRepository extends Repository<Auction> {

  void save(Auction newAuction);

  Optional<Auction> findOneBySlot(long slotId);

  Optional<Auction> find(long auctionId);
}
