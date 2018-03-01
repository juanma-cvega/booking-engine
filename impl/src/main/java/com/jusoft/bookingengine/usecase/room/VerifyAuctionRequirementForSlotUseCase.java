package com.jusoft.bookingengine.usecase.room;

import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VerifyAuctionRequirementForSlotUseCase {

  private final RoomManagerComponent roomManagerComponent;

  public void isAuctionRequiredFor(long roomId, long slotId) {
    roomManagerComponent.verifyAuctionRequirementFor(roomId, slotId);
  }
}
