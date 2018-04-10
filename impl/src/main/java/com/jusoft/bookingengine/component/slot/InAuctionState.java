package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;

import java.time.Clock;

class InAuctionState implements SlotState {

  private static final InAuctionState INSTANCE = new InAuctionState();

  private InAuctionState() {
  }

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  @Override
  public SlotState waitForAuction(Slot slot) {
    return InAuctionState.getInstance();
  }

  @Override
  public SlotState reserveForAuctionWinner(Slot slot) {
    return ReservedState.getInstance();
  }

  @Override
  public SlotState reserveForClass(Slot slot) {
    throw new SlotPendingAuctionException(slot.getId());
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock) {
    throw new SlotPendingAuctionException(slot.getId());
  }

  public static SlotState getInstance() {
    return INSTANCE;
  }
}
