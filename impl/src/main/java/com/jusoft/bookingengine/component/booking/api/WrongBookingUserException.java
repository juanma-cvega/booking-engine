package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class WrongBookingUserException extends RuntimeException {

    private static final long serialVersionUID = -3409360647626798706L;

    private static final String MESSAGE =
            "The provided user %s, does not match the expected user %s for booking %s";

    private final long providedId;
    private final long expectedId;
    private final long bookingId;

    public WrongBookingUserException(long providedId, long expectedId, long bookingId) {
        super(String.format(MESSAGE, providedId, expectedId, bookingId));
        this.providedId = providedId;
        this.expectedId = expectedId;
        this.bookingId = bookingId;
    }
}
