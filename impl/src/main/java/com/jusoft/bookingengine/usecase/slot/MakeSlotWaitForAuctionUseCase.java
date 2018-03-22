package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MakeSlotWaitForAuctionUseCase {

  private final SlotManagerComponent slotManagerComponent;

  public void makeSlotWaitForAuction(long slotId) {
    slotManagerComponent.makeWaitForAuction(slotId);
  }
}
