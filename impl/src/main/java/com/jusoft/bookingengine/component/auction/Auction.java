package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedException;
import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
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
  @NonNull
  private final Set<Bid> bidders;

  Auction(long id,
          long slotId,
          long roomId,
          ZonedDateTime startTime,
          ZonedDateTime endTime) {
    this(id, slotId, roomId, startTime, endTime, new HashSet<>());
  }

  Auction(long id,
          long slotId,
          long roomId,
          ZonedDateTime startTime,
          ZonedDateTime endTime,
          Set<Bid> bidders) {
    this.id = id;
    this.slotId = slotId;
    this.roomId = roomId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.bidders = new HashSet<>(bidders);
  }

  public Set<Bid> getBidders() {
    return new HashSet<>(bidders);
  }

  void addBidder(long bidder, Clock clock) {
    if (!isOpen(clock)) {
      throw new AuctionFinishedException(id, slotId, roomId);
    }
    bidders.add(new Bid(bidder, ZonedDateTime.now(clock)));
  }

  public boolean isOpen(Clock clock) {
    ZonedDateTime now = ZonedDateTime.now(clock);
    return now.isBefore(endTime) && (now.isAfter(startTime) || now.isEqual(startTime));
  }

  public Optional<Long> findAuctionWinner(AuctionWinnerStrategy strategy) {
    return strategy.findWinner(bidders);
  }
}
