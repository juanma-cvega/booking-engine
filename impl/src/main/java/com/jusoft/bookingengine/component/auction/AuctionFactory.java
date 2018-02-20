package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionFactory {

  private final Supplier<Long> idSupplier;
  private final Clock clock;

  public Auction createFrom(StartAuctionCommand command) {
    return new Auction(idSupplier.get(),
      command.getReferenceId(),
      ZonedDateTime.now(clock),
      command.getAuctionConfigInfo());
  }

  public AuctionView createFrom(Auction auction) {
    return AuctionView.of(auction.getId(),
      auction.getReferenceId(),
      auction.getOpenDate(),
      auction.getAuctionConfigInfo(),
      auction.getBidders());
  }
}
