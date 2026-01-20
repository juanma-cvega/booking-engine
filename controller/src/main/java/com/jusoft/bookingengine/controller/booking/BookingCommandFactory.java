package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;

class BookingCommandFactory {

    public CreateBookingCommand createFrom(long slotId, long userId) {
        return new CreateBookingCommand(userId, slotId);
    }
}
