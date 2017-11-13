package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.component.shared.Command;
import lombok.Data;

@Data
public class CreateBookingCommand implements Command {

  private final long userId;
  private final long roomId;
  private final long slotId;

}
