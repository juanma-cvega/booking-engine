package com.jusoft.bookingengine.component.auction.api;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateAuctionCommand {

  private final long slotId;
  private final long roomId;
  private final ZonedDateTime startTime;
  private final ZonedDateTime endTime;
}
