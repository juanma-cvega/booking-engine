package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotForAuctionWinnerUseCase {

  private final SlotManagerComponent slotManagerComponent;

  public void reserveSlotForAuctionWinner(long slotId, long userId) {
    slotManagerComponent.reserveSlotForAuctionWinner(slotId, userId);
  }
}
