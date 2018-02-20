package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class AuctionStartedEvent implements Event {

  private final long auctionId;
  private final long referenceId;
  @NonNull
  private final OpenDate openDate;
}
