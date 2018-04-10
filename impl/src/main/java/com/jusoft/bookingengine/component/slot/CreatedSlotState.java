package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotNotInAuctionException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatedSlotState implements SlotState {

  private static final CreatedSlotState INSTANCE = new CreatedSlotState();

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
    throw new SlotNotInAuctionException(slot.getId());
  }

  @Override
  public SlotState reserveForClass(Slot slot) {
    return ReservedState.getInstance();
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock) {
    throw new SlotNotAvailableException(slot.getId());
  }

  public static SlotState getInstance() {
    return INSTANCE;
  }
}
