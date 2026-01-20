package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotNotAvailableException extends RuntimeException {

    private static final long serialVersionUID = -6924770817544930562L;

    private static final String MESSAGE = "Slot %s is not available";

    private final long slotId;

    public SlotNotAvailableException(long slotId) {
        super(String.format(MESSAGE, slotId));
        this.slotId = slotId;
    }
}
