package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyPreReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import java.time.Clock;

record PreReservedState(SlotUser slotUser) implements SlotState {

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
