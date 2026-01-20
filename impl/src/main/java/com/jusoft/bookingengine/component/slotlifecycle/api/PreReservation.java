package com.jusoft.bookingengine.component.slotlifecycle.api;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class PreReservation {

    private final long userId;

    @NonNull private final ZonedDateTime reservationDate;
}
