package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.VerifyAuctionRequirementForSlotCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VerifyAuctionRequirementForSlotUseCase {

  private final RoomComponent roomComponent;

  public void isAuctionRequiredForSlot(VerifyAuctionRequirementForSlotCommand command) {
    roomComponent.verifyAuctionRequirementForSlot(command);
  }
}
