package com.jusoft.booking;

class WrongBookingUserException extends RuntimeException {

    private static final String MESSAGE = "The provided user %s, does not match the expected user %s for booking %s";

    WrongBookingUserException(long providedId, long expectedId, long bookingId) {
        super(String.format(MESSAGE, providedId, expectedId, bookingId));
    }
}
