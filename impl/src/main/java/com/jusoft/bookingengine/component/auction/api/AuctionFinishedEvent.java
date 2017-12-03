package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.shared.Event;
import lombok.Data;

@Data
public class AuctionFinishedEvent implements Event {

  private final long auctionId;
  private final long roomId;
  private final long slotId;
}
