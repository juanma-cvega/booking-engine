package com.jusoft.slot;

public class SlotNotFoundInRoomException extends RuntimeException {
    private static final String MESSAGE = "Slot %s not found in room %s";

    SlotNotFoundInRoomException(long slotId, long roomId) {
        super(String.format(MESSAGE, slotId, roomId));

    }
}
