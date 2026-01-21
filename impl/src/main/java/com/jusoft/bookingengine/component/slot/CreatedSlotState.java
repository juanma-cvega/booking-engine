package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import java.time.Clock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatedSlotState implements SlotState {

    private static final CreatedSlotState INSTANCE = new CreatedSlotState();

    @Override
    public SlotState makeAvailable(Slot slot) {
        return AvailableSlotState.getInstance();
    }

    @Override
    public SlotState reserve(Slot slot, Clock clock, SlotUser slotUser) {
        throw new SlotNotAvailableException(slot.getId());
    }

    @Override
    public SlotState preReserve(Slot slot, Clock clock, SlotUser slotUser) {
        if (!slot.isOpen(clock)) {
            throw new SlotNotOpenException(slot.getId());
        }
        return new PreReservedState(slotUser);
    }

    static SlotState getInstance() {
        return INSTANCE;
    }
}
