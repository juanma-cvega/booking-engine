package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionFactory {

    private final Supplier<Long> idSupplier;
    private final Clock clock;

    public Auction createFrom(StartAuctionCommand command) {
        return new Auction(
                idSupplier.get(),
                command.referenceId(),
                ZonedDateTime.now(clock),
                command.auctionConfigInfo());
    }

    public AuctionView createFrom(Auction auction) {
        return new AuctionView(
                auction.getId(),
                auction.getReferenceId(),
                auction.getOpenDate(),
                auction.getAuctionConfigInfo(),
                auction.getBidders());
    }
}
