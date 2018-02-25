package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.component.slot.api.SlotType;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotCoordinates {

  private final long userId;
  private final long clubId;
  private final long buildingId;
  private final long roomId;
  private final SlotType slotType;
}
