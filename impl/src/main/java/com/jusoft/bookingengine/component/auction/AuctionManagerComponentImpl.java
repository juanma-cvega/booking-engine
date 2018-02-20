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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.Optional;

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
    messagePublisher.publish(AuctionStartedEvent.of(newAuction.getId(),
      newAuction.getReferenceId(),
      newAuction.getOpenDate()));
    return auctionFactory.createFrom(newAuction);
  }

  @Override
  public void addBidderTo(long auctionId, long userId) {
    auctionRepository.execute(auctionId, auction -> {
      auction.addBidder(userId, clock);
      return auction;
    }, () -> new AuctionNotFoundException(auctionId));
  }

  @Override
  public void finishAuction(long auctionId, AuctionWinnerStrategy auctionWinnerStrategy) {
    Auction auction = auctionRepository
      .find(auctionId)
      .orElseThrow(() -> new AuctionNotFoundException(auctionId));
    Optional<Long> winnerFound = auction.findAuctionWinner(auctionWinnerStrategy);
    if (winnerFound.isPresent()) {
      Long auctionWinner = winnerFound.get();
      messagePublisher.publish(AuctionWinnerFoundEvent.of(auction.getId(), auctionWinner, auction.getReferenceId()));
    } else {
      messagePublisher.publish(AuctionUnsuccessfulEvent.of(auction.getId(), auction.getReferenceId()));
    }
  }

  @Override
  public AuctionView find(long auctionId) {
    Auction auction = auctionRepository.find(auctionId).orElseThrow(() -> new AuctionNotFoundException(auctionId));
    return auctionFactory.createFrom(auction);
  }
}
