package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionUnsuccessfulEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import java.time.Clock;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionManagerComponentImpl implements AuctionManagerComponent {

    private final AuctionRepository auctionRepository;
    private final AuctionFactory auctionFactory;
    private final MessagePublisher messagePublisher;
    private final Clock clock;

    @Override
    public AuctionView startAuction(StartAuctionCommand command) {
        Auction newAuction = auctionFactory.createFrom(command);
        auctionRepository.save(newAuction);
        messagePublisher.publish(
                new AuctionStartedEvent(
                        newAuction.getId(), newAuction.getReferenceId(), newAuction.getOpenDate()));
        return auctionFactory.createFrom(newAuction);
    }

    @Override
    public void addBidderToAuctionFor(long userId, long referenceId) {
        Auction auctionFound =
                auctionRepository
                        .findByReference(referenceId)
                        .orElseThrow(() -> new AuctionNotFoundException(referenceId));
        auctionRepository.execute(
                auctionFound.getId(),
                auction -> {
                    auction.addBidder(userId, clock);
                    return auction;
                },
                () -> new AuctionNotFoundException(referenceId));
    }

    @Override
    public void finishAuction(long auctionId, AuctionWinnerStrategy auctionWinnerStrategy) {
        Auction auction =
                auctionRepository
                        .find(auctionId)
                        .orElseThrow(() -> new AuctionNotFoundException(auctionId));
        Optional<Long> winnerFound = auction.findAuctionWinner(auctionWinnerStrategy);
        if (winnerFound.isPresent()) {
            Long auctionWinner = winnerFound.get();
            messagePublisher.publish(
                    new AuctionWinnerFoundEvent(
                            auction.getId(), auctionWinner, auction.getReferenceId()));
        } else {
            messagePublisher.publish(
                    new AuctionUnsuccessfulEvent(auction.getId(), auction.getReferenceId()));
        }
    }

    @Override
    public AuctionView find(long auctionId) {
        Auction auction =
                auctionRepository
                        .find(auctionId)
                        .orElseThrow(() -> new AuctionNotFoundException(auctionId));
        return auctionFactory.createFrom(auction);
    }
}
