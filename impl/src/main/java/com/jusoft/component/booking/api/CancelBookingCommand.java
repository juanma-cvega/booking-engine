package com.jusoft.component.booking.api;

import com.jusoft.component.shared.Command;
import lombok.Data;

@Data
public class CancelBookingCommand implements Command {

  private final long userId;
  private final long bookingId;
}
