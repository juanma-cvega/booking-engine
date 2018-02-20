package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class ReserveSlotCommand implements Command {

  private final long slotId;
  private final long userId;
}
