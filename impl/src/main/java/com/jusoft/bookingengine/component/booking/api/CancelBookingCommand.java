package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data
public class CancelBookingCommand implements Command {

  private final long userId;
  private final long bookingId;
}
