package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
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
  public AuctionView startAuction(CreateAuctionCommand createAuctionCommand) {
    Auction newAuction = auctionFactory.createFrom(createAuctionCommand);
    auctionRepository.save(newAuction);
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
    auction.findAuctionWinner(command.getAuctionWinnerStrategy()).ifPresent(auctionWinner ->
      messagePublisher.publish(new AuctionWinnerFoundEvent(auction.getId(), auctionWinner, auction.getSlotId(), auction.getRoomId())));
  }

  @Override
  public boolean isAuctionOpenForSlot(long slotId) {
    return auctionRepository.findOneBySlot(slotId).map(auction -> auction.isOpen(clock)).orElse(false);
  }

  @Override
  public Optional<AuctionView> find(long auctionId) {
    return auctionRepository.find(auctionId).map(auctionFactory::createFrom);
  }
}
