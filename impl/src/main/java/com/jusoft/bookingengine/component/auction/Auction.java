package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionConfig;
import lombok.Data;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Auction {

  private final long id;
  private final long slotId;
  private final long roomId;
  private final ZonedDateTime createdTime;
  private final ZonedDateTime endTime;
  //FIXME use library that uses primitives for collections?
  private final Set<Bid> buyers;
  private final Clock clock;

  Auction(long id,
          long slotId,
          long roomId,
          ZonedDateTime endTime,
          Clock clock) {
    this(id, slotId, roomId, endTime, new HashSet<>(), clock);
  }

  Auction(long id,
          long slotId,
          long roomId,
          ZonedDateTime endTime,
          Set<Bid> buyers,
          Clock clock) {
    this.id = id;
    this.slotId = slotId;
    this.roomId = roomId;
    this.createdTime = ZonedDateTime.now(clock);
    this.endTime = endTime;
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
    return now.isBefore(endTime) && now.isAfter(createdTime);
  }

  <T extends AuctionConfig> long findAuctionWinner(AuctionWinnerStrategy<T> strategy, T auctionConfig) {
    return strategy.findWinner(buyers, auctionConfig);
  }
}
