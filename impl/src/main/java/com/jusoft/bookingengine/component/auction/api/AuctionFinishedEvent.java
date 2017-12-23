package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data
public class AuctionFinishedEvent implements Event {

  private final long auctionId;
  private final long roomId;
  private final long slotId;
}
