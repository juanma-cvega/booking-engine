package com.jusoft.bookingengine.component.auction.api;

import lombok.Data;

@Data
public class CreateAuctionCommand {

  private final long slotId;
  private final long roomId;
  private final int durationInMinutes;
}
