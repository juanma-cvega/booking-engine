package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data
public class BookingCanceledEvent implements Event {

  private final long bookingId;

}
