package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyPreReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import lombok.Data;

import java.time.Clock;

@Data(staticConstructor = "of")
class PreReservedState implements SlotState {

  private final SlotUser slotUser;

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock, SlotUser slotUser) {
    throw new SlotAlreadyPreReservedException(slot.getId(), this.slotUser, slotUser);
  }

  @Override
  public SlotState preReserve(Slot slot, Clock clock, SlotUser slotUser) {
    if (!this.slotUser.equals(slotUser)) {
      throw new SlotAlreadyPreReservedException(slot.getId(), this.slotUser, slotUser);
    }
    return this;
  }
}
