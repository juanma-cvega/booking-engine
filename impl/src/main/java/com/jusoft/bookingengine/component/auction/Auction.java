package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedException;
import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Data;
import lombok.NonNull;

@Data
class Auction {

    private final long id;
    private final long referenceId;

    @NonNull private final OpenDate openDate;

    @NonNull private final AuctionConfigInfo auctionConfigInfo;

    @NonNull private final Set<Bid> bidders;

    Auction(
            long id,
            long referenceId,
            ZonedDateTime startTime,
            AuctionConfigInfo auctionConfigInfo) {
        this(id, referenceId, startTime, auctionConfigInfo, new HashSet<>());
    }

    Auction(
            long id,
            long referenceId,
            ZonedDateTime startTime,
            AuctionConfigInfo auctionConfigInfo,
            Set<Bid> bidders) {
        this.id = id;
        this.referenceId = referenceId;
        this.openDate =
                OpenDate.of(
                        startTime, startTime.plusMinutes(auctionConfigInfo.getAuctionDuration()));
        this.auctionConfigInfo = auctionConfigInfo;
        this.bidders = new HashSet<>(bidders);
    }

    public Set<Bid> getBidders() {
        return new HashSet<>(bidders);
    }

    void addBidder(long bidder, Clock clock) {
        if (!isOpen(clock)) {
            throw new AuctionFinishedException(id, referenceId);
        }
        bidders.add(new Bid(bidder, ZonedDateTime.now(clock)));
    }

    private boolean isOpen(Clock clock) {
        ZonedDateTime now = ZonedDateTime.now(clock);
        return now.isBefore(
                openDate.getEndTime()); // Now cannot be before start time as start time is always
        // now at creation time
    }

    public Optional<Long> findAuctionWinner(AuctionWinnerStrategy strategy) {
        return strategy.findWinner(bidders);
    }
}
