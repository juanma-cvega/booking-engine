package com.jusoft.component.slot;

public class SlotNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Slot %s not found in room %s";

    public SlotNotFoundException(long slotId, long roomId) {
        super(String.format(MESSAGE, slotId, roomId));
    }
}
