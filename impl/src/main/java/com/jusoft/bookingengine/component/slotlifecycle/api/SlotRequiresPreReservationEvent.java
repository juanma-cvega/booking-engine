package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;

public record SlotRequiresPreReservationEvent(long slotId, SlotUser slotUser) implements Event {}
