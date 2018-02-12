package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;

@Data
public class AuctionFinishedMessage implements InfrastructureMessage {

  private final long auctionId;
  private final long slotId;

}
