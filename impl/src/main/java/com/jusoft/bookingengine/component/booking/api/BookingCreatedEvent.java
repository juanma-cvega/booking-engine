package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Event;

public record BookingCreatedEvent(long bookingId, long userId, long slotId) implements Event {}
