package com.jusoft.bookingengine.component.slotlifecycle.api;

import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class ReservationDateNotValidException extends RuntimeException {

    private static final long serialVersionUID = 8036825786722830779L;

    private static final String MESSAGE = "Reservation date %s not valid for room %s";

    private final ZonedDateTime reservationDate;
    private final long roomId;

    public ReservationDateNotValidException(ZonedDateTime reservationDate, long roomId) {
        super(String.format(MESSAGE, reservationDate, roomId));
        this.reservationDate = reservationDate;
        this.roomId = roomId;
    }
}
