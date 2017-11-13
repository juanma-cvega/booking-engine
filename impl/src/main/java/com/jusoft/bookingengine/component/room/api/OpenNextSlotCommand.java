package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.shared.Command;
import lombok.Data;

@Data
public class OpenNextSlotCommand implements Command {

  private final long roomId;

}
