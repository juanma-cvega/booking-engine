package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.booking.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotNotInAuctionException;

import java.time.Clock;

import static com.jusoft.bookingengine.component.slot.SlotState.State.AVAILABLE;

class ReservedState implements SlotState {

  private static final ReservedState INSTANCE = new ReservedState();

  private ReservedState() {
  }

  @Override
  public State makeAvailable(Slot slot) {
    return AVAILABLE;
  }

  @Override
  public State reserveForAuctionWinner(Slot slot) {
    throw new SlotNotInAuctionException(slot.getId());
  }

  @Override
  public State reserve(Slot slot, Clock clock) {
    throw new SlotAlreadyReservedException(slot.getId());
  }

  public static SlotState getInstance() {
    return INSTANCE;
  }
}
