package com.jusoft.bookingengine.component.room.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class VerifyAuctionRequirementForSlotCommand {

  private final long roomId;
  private final long slotId;
}
