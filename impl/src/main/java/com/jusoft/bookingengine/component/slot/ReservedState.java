package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotNotInAuctionException;

import java.time.Clock;

class ReservedState implements SlotState {

  private static final ReservedState INSTANCE = new ReservedState();

  private ReservedState() {
  }

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  @Override
  public SlotState waitForAuction(Slot slot) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  @Override
  public SlotState reserveForAuctionWinner(Slot slot) {
    throw new SlotNotInAuctionException(slot.getId());
  }

  @Override
  public SlotState reserveForClass(Slot slot) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  public static SlotState getInstance() {
    return INSTANCE;
  }
}
