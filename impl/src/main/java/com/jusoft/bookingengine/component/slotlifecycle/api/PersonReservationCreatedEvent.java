package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class PersonReservationCreatedEvent implements Event {

    private final long slotId;
    private final long userId;
}
