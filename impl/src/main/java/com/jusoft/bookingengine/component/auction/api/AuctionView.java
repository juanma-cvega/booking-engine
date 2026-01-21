package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import java.util.Objects;
import java.util.Set;

public record AuctionView(
        long id,
        long referenceId,
        OpenDate openDate,
        AuctionConfigInfo auctionConfigInfo,
        Set<Bid> bidders) {
    public AuctionView {
        Objects.requireNonNull(openDate, "openDate must not be null");
        Objects.requireNonNull(auctionConfigInfo, "auctionConfigInfo must not be null");
        Objects.requireNonNull(bidders, "bidders must not be null");
        bidders = Set.copyOf(bidders);
    }
}
