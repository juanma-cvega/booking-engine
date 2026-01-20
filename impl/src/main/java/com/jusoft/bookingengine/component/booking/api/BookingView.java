package com.jusoft.bookingengine.component.booking.api;

import java.time.ZonedDateTime;
import java.util.Objects;

public record BookingView(long id, long userId, ZonedDateTime bookingTime, long slotId) {
    public BookingView {
        Objects.requireNonNull(bookingTime, "bookingTime must not be null");
    }
}
