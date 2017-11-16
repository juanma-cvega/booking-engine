package com.jusoft.bookingengine.component.auction;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AuctionRepositoryInMemory implements AuctionRepository {

  private final Map<Long, Auction> store = new ConcurrentHashMap<>();

  @Override
  public void save(Auction newAuction) {
    store.put(newAuction.getId(), newAuction);
  }

  @Override
  public Optional<Auction> findOneBySlot(long slotId) {
    return store.values().stream().filter(auction -> auction.getSlotId() == slotId).findFirst();
  }

  @Override
  public Optional<Auction> find(long auctionId) {
    return Optional.ofNullable(store.get(auctionId));
  }
}
