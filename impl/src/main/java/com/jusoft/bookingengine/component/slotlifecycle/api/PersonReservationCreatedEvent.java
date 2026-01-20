package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;

public record PersonReservationCreatedEvent(long slotId, long userId) implements Event {}
