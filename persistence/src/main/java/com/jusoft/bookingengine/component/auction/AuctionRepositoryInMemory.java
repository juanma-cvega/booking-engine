package com.jusoft.bookingengine.component.auction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionRepositoryInMemory implements AuctionRepository {

  private final Map<Long, Auction> store;
  private final Lock lock;

  @Override
  public void save(Auction newAuction) {
    store.put(newAuction.getId(), newAuction);
  }

  @Override
  public Optional<Auction> find(long auctionId) {
    Auction value = store.get(auctionId);
    if (value != null) {
      return Optional.of(copyAuction(value));
    }
    return Optional.empty();
  }

  private Auction copyAuction(Auction value) {
    return new Auction(value.getId(),
      value.getReferenceId(),
      value.getOpenDate().getStartTime(),
      value.getAuctionConfigInfo(),
      value.getBidders());
  }

  @Override
  public void execute(long auctionId, UnaryOperator<Auction> function, Supplier<RuntimeException> notFoundException) {
    withLock(lock, () -> {
      Auction auction = find(auctionId).orElseThrow(notFoundException);
      Auction auctionModified = function.apply(auction);
      store.put(auctionId, auctionModified);
    });
  }

  @Override
  public Optional<Auction> findByReference(long referenceId) {
    return store.values().stream()
      .filter(auction -> auction.getReferenceId() == referenceId)
      .findFirst()
      .map(this::copyAuction);
  }
}
