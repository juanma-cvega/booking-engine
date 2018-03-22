package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotAlreadyAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotNotInAuctionException;

import java.time.Clock;
import java.time.ZonedDateTime;

class AvailableSlotState implements SlotState {

  private static final AvailableSlotState INSTANCE = new AvailableSlotState();

  private AvailableSlotState() {
  }

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  @Override
  public SlotState waitForAuction(Slot slot) {
    throw new SlotAlreadyAvailableException(slot.getId());
  }

  @Override
  public SlotState reserveForAuctionWinner(Slot slot) {
    throw new SlotNotInAuctionException(slot.getId());
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock) {
    if (!isAvailable(slot, clock)) {
      throw new SlotNotAvailableException(slot.getId());
    }
    return ReservedState.getInstance();
  }

  /**
   * A slot is only available if the current time is before the slot starting time
   *
   * @param slot  Slot the state is attached to
   * @param clock The system clock
   * @return whether the slot is available for booking
   */
  @Override
  public boolean isAvailable(Slot slot, Clock clock) {
    ZonedDateTime now = ZonedDateTime.now(clock);
    return slot.getOpenDate().getStartTime().compareTo(now) > 0;
  }

  public static SlotState getInstance() {
    return INSTANCE;
  }
}
