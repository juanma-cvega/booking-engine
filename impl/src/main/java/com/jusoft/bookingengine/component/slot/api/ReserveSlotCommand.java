package com.jusoft.bookingengine.component.slot.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class ReserveSlotCommand {

  private final long slotId;
  private final long userId;
}
