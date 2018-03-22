package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.Data;

@Data
public class NextSlotConfig {

  private final long buildingId;
  private final long clubId;
  private final OpenDate openDate;
}
