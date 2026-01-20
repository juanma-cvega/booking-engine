package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotNotOpenException extends RuntimeException {

    private static final long serialVersionUID = -6924770817544930562L;

    private static final String MESSAGE = "Slot %s is past the start time";

    private final long slotId;

    public SlotNotOpenException(long slotId) {
        super(String.format(MESSAGE, slotId));
        this.slotId = slotId;
    }
}
