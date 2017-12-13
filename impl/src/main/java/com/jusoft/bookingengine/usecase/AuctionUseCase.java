package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AuctionUseCase {

  private final RoomComponent roomComponent;
  private final AuctionComponent auctionComponent;
  private final MessagePublisher messagePublisher;

  public void startAuction(long roomId, long slotId) {
    int auctionDuration = roomComponent.getAuctionDurationFor(roomId);
    if (isAuctionEnabledFor(auctionDuration)) {
      AuctionView newAuction = auctionComponent.startAuction(new CreateAuctionCommand(slotId, roomId, auctionDuration));
      AuctionFinishedEvent message = new AuctionFinishedEvent(newAuction.getId(), newAuction.getRoomId(), newAuction.getSlotId());
      messagePublisher.publish(new ScheduledEvent(message, newAuction.getEndTime()));
    }
  }

  private boolean isAuctionEnabledFor(int auctionDuration) {
    return auctionDuration > 0;
  }

  public void finishAuction(long auctionId, long roomId) {
    RoomView room = roomComponent.find(roomId);
    auctionComponent.finishAuction(new FinishAuctionCommand(auctionId, room.getAuctionConfigInfo()));
  }

  public void addBuyerTo(long slotId, long userId) {
    auctionComponent.addBuyerTo(slotId, userId);
  }
}
