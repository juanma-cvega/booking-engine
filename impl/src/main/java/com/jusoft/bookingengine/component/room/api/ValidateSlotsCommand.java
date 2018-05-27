package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.Data;

import java.util.Set;

@Data(staticConstructor = "of")
public class ValidateSlotsCommand {

  private final long roomId;
  private final Set<SystemLocalTime> slotsTime;
}
