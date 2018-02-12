package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data
public class MakeSlotAvailableCommand implements Command {

  private final long slotId;
}
