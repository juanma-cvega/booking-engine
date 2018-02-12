package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotForAuctionWinnerUseCase {

  private final SlotComponent slotComponent;

  public void reserveSlotForAuctionWinner(ReserveSlotCommand command) {
    slotComponent.reserveSlotForAuctionWinner(command);
  }
}
