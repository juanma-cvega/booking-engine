package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.Data;

@Data
public class NextSlotConfig {

  private final OpenDate openDate;
  private final SlotState state;
}
