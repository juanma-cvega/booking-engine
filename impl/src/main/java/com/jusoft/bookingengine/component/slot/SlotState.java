package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotUser;
import java.time.Clock;

interface SlotState {

    SlotState makeAvailable(Slot slot);

    SlotState reserve(Slot slot, Clock clock, SlotUser slotUser);

    SlotState preReserve(Slot slot, Clock clock, SlotUser slotUser);
}
