package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionFactory {

  private final Supplier<Long> idSupplier;
  private final Clock clock;

  public Auction createFrom(CreateAuctionCommand createAuctionCommand) {
    return new Auction(idSupplier.get(),
      createAuctionCommand.getSlotId(),
      createAuctionCommand.getRoomId(),
      createAuctionCommand.getDurationInMinutes(),
      clock);
  }
}
