package com.jusoft.booking;

class SlotAlreadyBookedException extends RuntimeException {
    private static final String MESSAGE = "Slot %s in room %s already taken";

    SlotAlreadyBookedException(long roomId, long slotId) {
        super(String.format(MESSAGE, roomId, slotId));
    }
}
