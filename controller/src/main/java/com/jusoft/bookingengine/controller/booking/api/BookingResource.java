package com.jusoft.bookingengine.controller.booking.api;

import static com.jusoft.bookingengine.util.TimeUtil.getTimeFrom;

import com.jusoft.bookingengine.component.booking.api.BookingView;

public record BookingResource(long bookingId, long userId, long bookingTime, long slotId) {

    public static BookingResource from(BookingView booking) {
        return new BookingResource(
                booking.id(),
                booking.userId(),
                getTimeFrom(booking.bookingTime()),
                booking.slotId());
    }
}
