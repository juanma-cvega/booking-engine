package com.jusoft.bookingengine.component.auction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.jusoft.bookingengine.util.LockingTemplate.tryCompareAndSwap;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionRepositoryInMemory implements AuctionRepository {

  private final Map<Long, Auction> store;

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

  @Override
  public void execute(long auctionId, UnaryOperator<Auction> function, Supplier<RuntimeException> notFoundException) {
    tryCompareAndSwap(() -> {
      Auction auction = find(auctionId).orElseThrow(notFoundException);
      return store.replace(auctionId, auction, function.apply(auction));
    });
  }
}
