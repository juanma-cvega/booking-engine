package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Event;

public record SlotMadeAvailableEvent(long slotId) implements Event {}
