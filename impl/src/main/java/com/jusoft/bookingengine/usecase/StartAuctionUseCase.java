package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class StartAuctionUseCase {

  private final AuctionComponent auctionComponent;
  private final RoomComponent roomComponent;
  private final SchedulerComponent schedulerComponent;

  public Optional<AuctionView> startAuction(long roomId, long slotId) {
    Optional<AuctionView> auctionCreated = Optional.empty();
    int auctionDuration = roomComponent.getAuctionDurationFor(roomId);
    if (isAuctionEnabledFor(auctionDuration)) {
      AuctionView newAuction = auctionComponent.startAuction(new CreateAuctionCommand(slotId, roomId, auctionDuration));
      AuctionFinishedEvent message = new AuctionFinishedEvent(newAuction.getId(), newAuction.getRoomId(), newAuction.getSlotId());
      schedulerComponent.schedule(newAuction.getEndTime(), message);
      auctionCreated = Optional.of(newAuction);
    }
    return auctionCreated;
  }

  private boolean isAuctionEnabledFor(int auctionDuration) {
    return auctionDuration > 0;
  }
}
