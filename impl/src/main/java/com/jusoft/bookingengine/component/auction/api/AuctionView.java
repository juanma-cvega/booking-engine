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
  private final Set<Bid> buyers;

  public AuctionView(long id,
                     long slotId,
                     long roomId,
                     ZonedDateTime startTime,
                     ZonedDateTime endTime,
                     Set<Bid> buyers) {
    this.id = id;
    this.slotId = slotId;
    this.roomId = roomId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.buyers = new HashSet<>(buyers);
  }

  public Set<Bid> getBuyers() {
    return new HashSet<>(buyers);
  }
}
