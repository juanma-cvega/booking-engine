package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class AuctionUnsuccessfulEvent implements Event {

  private final long auctionId;
  private final long referenceId;
}
