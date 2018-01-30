package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;

@AllArgsConstructor
public class StartAuctionUseCase {

  private final AuctionComponent auctionComponent;
  private final RoomComponent roomComponent;
  private final SchedulerComponent schedulerComponent;
  private final Clock clock;

  public Optional<AuctionView> startAuction(long roomId, long slotId) {
    Optional<AuctionView> auctionCreated = Optional.empty();
    int auctionDuration = roomComponent.getAuctionDurationFor(roomId);
    if (isAuctionEnabledFor(auctionDuration)) {
      ZonedDateTime startTime = ZonedDateTime.now(clock);
      AuctionView newAuction = auctionComponent.startAuction(new CreateAuctionCommand(slotId, roomId, startTime, startTime.plusMinutes(auctionDuration)));
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
