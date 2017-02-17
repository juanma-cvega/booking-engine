package com.jusoft.booking;

class WrongUserForSlotException extends RuntimeException {

    private static final String MESSAGE = "The provided user %s, does not match the expected user %s for slot %s";

    WrongUserForSlotException(long providedId, long expectedId, long slotId) {
        super(String.format(MESSAGE, providedId, expectedId, slotId));
    }
}
