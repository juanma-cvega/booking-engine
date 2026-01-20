package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Event;

public record SlotReservedEvent(long slotId, SlotUser slotUser) implements Event {}
