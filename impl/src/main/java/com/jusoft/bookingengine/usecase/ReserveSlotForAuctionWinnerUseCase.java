package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotForAuctionWinnerUseCase {

  private final SlotManagerComponent slotManagerComponent;

  public void reserveSlotForAuctionWinner(ReserveSlotCommand command) {
    slotManagerComponent.reserveSlotForAuctionWinner(command);
  }
}
