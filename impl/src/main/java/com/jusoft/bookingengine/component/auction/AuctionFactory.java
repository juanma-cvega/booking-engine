package com.jusoft.bookingengine.component.auction;

import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@AllArgsConstructor
class AuctionFactory {

  private final Supplier<Long> idSupplier;
  private final Clock clock;

  public Auction createFrom(long slotId, long roomId, ZonedDateTime endTime) {
    return new Auction(idSupplier.get(), slotId, roomId, endTime, clock);
  }
}
