package com.jusoft.component.booking;

import java.time.LocalDateTime;

public class SlotAlreadyStartedException extends RuntimeException {

    private static final String MESSAGE = "Slot %s in room %s is past the start time at %s";

    public SlotAlreadyStartedException(long slotId, long roomId, LocalDateTime startTime) {
        super(String.format(MESSAGE, slotId, roomId, startTime));
    }
}
