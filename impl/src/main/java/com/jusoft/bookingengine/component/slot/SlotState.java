package com.jusoft.bookingengine.component.slot;

import java.time.Clock;

interface SlotState {

  SlotState makeAvailable(Slot slot);

  SlotState reserve(Slot slot, Clock clock);

  SlotState preReserve(Slot slot, Clock clock);

}
