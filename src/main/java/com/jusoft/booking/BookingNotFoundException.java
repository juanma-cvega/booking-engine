package com.jusoft.booking;

class BookingNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Booking %s not found for user %s";

    BookingNotFoundException(long userId, long bookingId) {
        super(String.format(MESSAGE, bookingId, userId));
    }
}
