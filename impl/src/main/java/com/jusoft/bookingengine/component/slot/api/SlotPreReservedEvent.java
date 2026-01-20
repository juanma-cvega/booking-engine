package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Event;

public record SlotPreReservedEvent(long slotId, SlotUser slotUser) implements Event {}
