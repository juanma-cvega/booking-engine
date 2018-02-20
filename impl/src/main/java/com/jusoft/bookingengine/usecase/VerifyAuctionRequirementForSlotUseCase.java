package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.VerifyAuctionRequirementForSlotCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VerifyAuctionRequirementForSlotUseCase {

  private final RoomManagerComponent roomManagerComponent;

  public void isAuctionRequiredForSlot(VerifyAuctionRequirementForSlotCommand command) {
    roomManagerComponent.verifyAuctionRequirementForSlot(command);
  }
}
