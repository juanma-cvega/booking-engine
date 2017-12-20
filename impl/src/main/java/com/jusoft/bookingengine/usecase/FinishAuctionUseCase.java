package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FinishAuctionUseCase {

  private final RoomComponent roomComponent;
  private final AuctionComponent auctionComponent;

  public void finishAuction(long auctionId, long roomId) {
    RoomView room = roomComponent.find(roomId);
    auctionComponent.finishAuction(new FinishAuctionCommand(auctionId, room.getAuctionConfigInfo()));
  }
}
