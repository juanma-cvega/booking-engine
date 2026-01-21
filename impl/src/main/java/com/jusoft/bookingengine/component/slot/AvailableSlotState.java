package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import java.time.Clock;

class AvailableSlotState implements SlotState {

    private static final AvailableSlotState INSTANCE = new AvailableSlotState();

    private AvailableSlotState() {}

    @Override
    public SlotState makeAvailable(Slot slot) {
        return AvailableSlotState.getInstance();
    }

    @Override
    public SlotState reserve(Slot slot, Clock clock, SlotUser slotUser) {
        checkIfOpen(slot, clock);
        return ReservedState.of(slotUser);
    }

    @Override
    public SlotState preReserve(Slot slot, Clock clock, SlotUser slotUser) {
        checkIfOpen(slot, clock);
        return new PreReservedState(slotUser);
    }

    private void checkIfOpen(Slot slot, Clock clock) {
        if (!slot.isOpen(clock)) {
            throw new SlotNotOpenException(slot.getId());
        }
    }

    static SlotState getInstance() {
        return INSTANCE;
    }
}
