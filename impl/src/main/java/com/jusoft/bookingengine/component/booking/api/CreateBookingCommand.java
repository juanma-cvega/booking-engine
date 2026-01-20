package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Command;

public record CreateBookingCommand(long userId, long slotId) implements Command {}
