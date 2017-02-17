package com.jusoft.slot;

public class SlotNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Slot %s not found";

    SlotNotFoundException(long slotId) {
        super(String.format(MESSAGE, slotId));
    }
}
