package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import lombok.Data;
import lombok.NonNull;

import java.time.Clock;

@Data(staticConstructor = "of")
class ReservedState implements SlotState {

  @NonNull
  private final SlotUser slotUser;

  @Override
  public SlotState makeAvailable(Slot slot) {
    return AvailableSlotState.getInstance();
  }

  @Override
  public SlotState reserve(Slot slot, Clock clock, SlotUser slotUser) {
    if (!this.slotUser.equals(slotUser)) {
      throw new SlotAlreadyReservedException(slot.getId(), this.slotUser, slotUser);
    }
    return this;
  }

  @Override
  public SlotState preReserve(Slot slot, Clock clock, SlotUser slotUser) {
    throw new SlotAlreadyReservedException(slot.getId(), this.slotUser, slotUser);
  }
}
