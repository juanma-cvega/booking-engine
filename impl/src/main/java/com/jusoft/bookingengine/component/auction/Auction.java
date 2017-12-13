package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.Bid;
import lombok.Data;
import lombok.NonNull;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
class Auction {

  private final long id;
  private final long slotId;
  private final long roomId;
  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;
  //FIXME use library that uses primitives for collections?
  @NonNull
  private final Set<Bid> buyers;
  @NonNull
  private final Clock clock;

  Auction(long id,
          long slotId,
          long roomId,
          int durationInMinutes,
          Clock clock) {
    this(id, slotId, roomId, durationInMinutes, new HashSet<>(), clock);
  }

  Auction(long id,
          long slotId,
          long roomId,
          int durationInMinutes,
          Set<Bid> buyers,
          Clock clock) {
    this.id = id;
    this.slotId = slotId;
    this.roomId = roomId;
    this.startTime = ZonedDateTime.now(clock);
    this.endTime = startTime.plusMinutes(durationInMinutes);
    this.buyers = new HashSet<>(buyers);
    this.clock = clock;
  }

  public Set<Bid> getBuyers() {
    return new HashSet<>(buyers);
  }

  void addBuyers(long buyer) {
    buyers.add(new Bid(buyer, ZonedDateTime.now(clock)));
  }

  public boolean isOpen() {
    ZonedDateTime now = ZonedDateTime.now(clock);
    return now.isBefore(endTime) && now.isAfter(startTime);
  }

  public Optional<Long> findAuctionWinner(AuctionWinnerStrategy strategy) {
    return strategy.findWinner(buyers);
  }
}
