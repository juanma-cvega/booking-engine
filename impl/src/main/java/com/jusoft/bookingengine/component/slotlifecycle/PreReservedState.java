package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;

record PreReservedState(long slotId, SlotUser user) implements NextSlotState {}
