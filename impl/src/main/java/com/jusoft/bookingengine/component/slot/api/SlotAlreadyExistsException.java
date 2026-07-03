package com.jusoft.bookingengine.component.slot.api;

import lombok.Getter;

@Getter
public class SlotAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 8895151433721358583L;

    private static final String MESSAGE = "Slot %s already exists.";

    private final long slotId;

    public SlotAlreadyExistsException(long slotId) {
        super(String.format(MESSAGE, slotId));
        this.slotId = slotId;
    }
}
