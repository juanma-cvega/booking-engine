package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;

public record SlotCanBeMadeAvailableEvent(long slotId) implements Event {}
