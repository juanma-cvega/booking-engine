package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class SlotsTimetableInvalidException extends RuntimeException {

    private static final long serialVersionUID = 6869485460103964837L;

    private static final String MESSAGE = "Slots timetable %s does not cover current reservations";

    private final long roomId;
    private final transient SlotsTimetable slotsTimetable;

    public SlotsTimetableInvalidException(long roomId, SlotsTimetable slotsTimetable) {
        this.roomId = roomId;
        this.slotsTimetable = slotsTimetable;
    }
}
