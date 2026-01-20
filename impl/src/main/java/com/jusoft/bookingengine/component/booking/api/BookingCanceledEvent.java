package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Event;

public record BookingCanceledEvent(long bookingId) implements Event {}
