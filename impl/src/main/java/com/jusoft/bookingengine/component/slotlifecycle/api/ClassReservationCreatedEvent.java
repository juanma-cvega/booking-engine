package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;

public record ClassReservationCreatedEvent(long slotId, long classId) implements Event {}
