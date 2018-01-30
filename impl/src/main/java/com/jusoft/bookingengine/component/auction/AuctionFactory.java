package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionFactory {

  private final Supplier<Long> idSupplier;

  public Auction createFrom(CreateAuctionCommand createAuctionCommand) {
    return new Auction(idSupplier.get(),
      createAuctionCommand.getSlotId(),
      createAuctionCommand.getRoomId(),
      createAuctionCommand.getStartTime(),
      createAuctionCommand.getEndTime());
  }

  public AuctionView createFrom(Auction auction) {
    return new AuctionView(auction.getId(),
      auction.getSlotId(),
      auction.getRoomId(),
      auction.getStartTime(),
      auction.getEndTime(),
      auction.getBidders());
  }
}
