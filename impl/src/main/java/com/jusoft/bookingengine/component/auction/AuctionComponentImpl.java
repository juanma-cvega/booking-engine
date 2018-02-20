package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.component.slot.api.MakeSlotAvailableCommand;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionComponentImpl implements AuctionComponent {

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
  public void finishAuction(FinishAuctionCommand command) {
    Auction auction = auctionRepository
      .find(command.getAuctionId())
      .orElseThrow(() -> new AuctionNotFoundException(command.getAuctionId()));
    Optional<Long> winnerFound = auction.findAuctionWinner(command.getAuctionWinnerStrategy());
    if (winnerFound.isPresent()) {
      Long auctionWinner = winnerFound.get();
      messagePublisher.publish(AuctionWinnerFoundEvent.of(auction.getId(), auctionWinner, auction.getReferenceId()));
    } else {
      messagePublisher.publish(MakeSlotAvailableCommand.of(auction.getReferenceId()));
    }
  }

  @Override
  public AuctionView find(long auctionId) {
    Auction auction = auctionRepository.find(auctionId).orElseThrow(() -> new AuctionNotFoundException(auctionId));
    return auctionFactory.createFrom(auction);
  }
}
