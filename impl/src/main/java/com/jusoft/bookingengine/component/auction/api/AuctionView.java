package com.jusoft.bookingengine.component.auction.api;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class AuctionView {

  private final long id;
  private final long slotId;
  private final long roomId;
  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;
  @NonNull
  private final Set<Bid> bidders;

  public AuctionView(long id,
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
}
