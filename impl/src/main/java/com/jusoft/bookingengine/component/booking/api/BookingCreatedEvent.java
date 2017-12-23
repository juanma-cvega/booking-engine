package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data
public class BookingCreatedEvent implements Event {

  private final long bookingId;
  private final long userId;
  private final long slotId;
}
