package com.jusoft.component.booking.api;

import com.jusoft.component.shared.Command;
import lombok.Data;

@Data
public class CreateBookingCommand implements Command {

  private final long userId;
  private final long roomId;
  private final long slotId;

}
