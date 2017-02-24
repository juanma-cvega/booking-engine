package com.jusoft.controller.booking;

import com.jusoft.component.booking.CancelBookingCommand;
import com.jusoft.component.booking.CreateBookingCommand;

class BookingCommandFactory {

    public CancelBookingCommand createFrom(Long userId, Long bookingId) {
        return new CancelBookingCommand(userId, bookingId);
    }

    public CreateBookingCommand createFrom(CreateBookingRequest request) {
        return new CreateBookingCommand(request.getUserId(), request.getRoomId(), request.getSlotId());
    }
}
