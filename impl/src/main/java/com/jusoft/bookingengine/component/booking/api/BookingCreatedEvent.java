package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.component.shared.Event;
import lombok.Data;

@Data
public class BookingCreatedEvent implements Event {

  private final long bookingId;
  private final long userId;
  private final long slotId;
}
