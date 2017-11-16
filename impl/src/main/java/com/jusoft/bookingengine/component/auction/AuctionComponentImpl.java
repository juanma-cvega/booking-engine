package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.auction.api.SlotNotInAuctionException;
import com.jusoft.bookingengine.component.room.Room;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
class AuctionComponentImpl implements AuctionComponent {

  private final AuctionRepository auctionRepository;
  private final AuctionStrategyRegistrar auctionStrategyRegistrar;
  private final AuctionFactory auctionFactory;
  private final RoomComponent roomComponent;
  private final MessagePublisher messagePublisher;

  @Override
  public void startAuction(long slotId, long roomId) {
    ZonedDateTime auctionEndTime = roomComponent.findAuctionEndTimeFor(roomId, slotId);
    Auction newAuction = auctionFactory.createFrom(slotId, roomId, auctionEndTime);
    auctionRepository.save(newAuction);
    messagePublisher.publish(new AuctionStartedEvent(newAuction.getId(), slotId, newAuction.getEndTime()));
  }

  @Override
  public void addBuyerTo(long slotId, long userId) {
    Auction auction = findBySlot(slotId);
    auction.addBuyers(userId);
  }

  @Override
  public Auction findBySlot(long slotId) {
    return auctionRepository.findOneBySlot(slotId).orElseThrow(() -> new SlotNotInAuctionException(slotId));
  }

  @Override
  public void finishAuction(long auctionId) {
    Auction auction = auctionRepository.find(auctionId).orElseThrow(() -> new AuctionNotFoundException(auctionId));
    Room room = roomComponent.find(auction.getRoomId());
    AuctionWinnerStrategy strategy = auctionStrategyRegistrar.findStrategyFor(room.getStrategyType());
    long auctionWinner = auction.findAuctionWinner(strategy, room.getAuctionConfig());
    messagePublisher.publish(new AuctionWinnerFoundEvent(auction.getId(), auctionWinner, auction.getSlotId(), auction.getRoomId()));
  }

  @Override
  public boolean isAuctionOpenForSlot(long slotId) {
    return findBySlot(slotId).isOpen();
  }
}
