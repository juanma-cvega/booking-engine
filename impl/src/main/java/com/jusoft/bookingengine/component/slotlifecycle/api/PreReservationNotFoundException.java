package com.jusoft.bookingengine.component.slotlifecycle.api;

import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class PreReservationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3721681472894667799L;

    private static final String MESSAGE = "Pre reservation at %s not found in room %s";

    private final long roomId;
    private final ZonedDateTime slotStartTime;

    public PreReservationNotFoundException(long roomId, ZonedDateTime slotStartTime) {
        super(String.format(MESSAGE, slotStartTime, roomId));
        this.roomId = roomId;
        this.slotStartTime = slotStartTime;
    }
}
