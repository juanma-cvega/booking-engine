package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.shared.Message;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class AuctionStartedEvent implements Message {

  private final long auctionId;
  private final long slotId;
  @NonNull
  private final ZonedDateTime auctionEndTime;
}
