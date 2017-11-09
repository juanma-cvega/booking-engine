package com.jusoft.component.booking.api;

public class BookingNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Booking %s not found for user %s";

    public BookingNotFoundException(long userId, long bookingId) {
        super(String.format(MESSAGE, bookingId, userId));
    }
}
