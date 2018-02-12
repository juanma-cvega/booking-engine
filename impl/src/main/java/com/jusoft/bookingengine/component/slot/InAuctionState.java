package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;

import java.time.Clock;

import static com.jusoft.bookingengine.component.slot.SlotState.State.AVAILABLE;
import static com.jusoft.bookingengine.component.slot.SlotState.State.RESERVED;

class InAuctionState implements SlotState {

  private static final InAuctionState INSTANCE = new InAuctionState();

  private InAuctionState() {
  }

  @Override
  public State makeAvailable(Slot slot) {
    return AVAILABLE;
  }

  @Override
  public State reserveForAuctionWinner(Slot slot) {
    return RESERVED;
  }

  @Override
  public State reserve(Slot slot, Clock clock) {
    throw new SlotPendingAuctionException(slot.getId());
  }

  public static SlotState getInstance() {
    return INSTANCE;
  }
}
