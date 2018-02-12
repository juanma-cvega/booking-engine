package com.jusoft.bookingengine.component.slot;

import java.time.Clock;

interface SlotState {

  enum State {
    AVAILABLE(AvailableSlotState.getInstance()),
    IN_AUCTION(InAuctionState.getInstance()),
    RESERVED(ReservedState.getInstance());

    private final SlotState slotState;

    State(SlotState instance) {
      this.slotState = instance;
    }

    public SlotState getSlotState() {
      return this.slotState;
    }
  }

  State makeAvailable(Slot slot);

  State reserveForAuctionWinner(Slot slot);

  State reserve(Slot slot, Clock clock);

  default boolean isAvailable(Slot slot, Clock clock) {
    return false;
  }
}
