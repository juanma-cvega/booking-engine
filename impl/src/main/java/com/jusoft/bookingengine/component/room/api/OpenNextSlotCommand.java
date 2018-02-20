package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class OpenNextSlotCommand implements Command {

  private final long roomId;

}
