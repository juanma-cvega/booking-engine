package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.CreateAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class StartAuctionUseCase {

  private final AuctionComponent auctionComponent;
  private final RoomComponent roomComponent;

  public Optional<AuctionView> startAuction(long roomId, long slotId) {
    Optional<AuctionView> auctionCreated = Optional.empty();
    int auctionDuration = roomComponent.getAuctionDurationFor(roomId);
    if (isAuctionEnabledFor(auctionDuration)) {
      auctionCreated = Optional.of(auctionComponent.startAuction(new CreateAuctionCommand(slotId, roomId, auctionDuration)));
    }
    return auctionCreated;
  }

  private boolean isAuctionEnabledFor(int auctionDuration) {
    return auctionDuration > 0;
  }
}
