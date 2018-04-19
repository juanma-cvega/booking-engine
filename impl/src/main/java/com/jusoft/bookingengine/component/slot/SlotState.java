package com.jusoft.bookingengine.component.slot;

import java.time.Clock;

interface SlotState {

  SlotState makeAvailable(Slot slot);

  SlotState waitForAuction(Slot slot);

  SlotState reserveForAuctionWinner(Slot slot);

  SlotState reserveForClass(Slot slot);

  SlotState reserve(Slot slot, Clock clock);

  default boolean isOpen(Slot slot, Clock clock) {
    return false;
  }
}
