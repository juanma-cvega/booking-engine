package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class BookingCanceledEvent implements Event {

  private final long bookingId;

}
