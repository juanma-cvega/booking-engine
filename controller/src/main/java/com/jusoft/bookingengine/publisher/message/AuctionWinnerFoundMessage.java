package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;

@Data(staticConstructor = "of")
public class AuctionWinnerFoundMessage implements InfrastructureMessage {

  private final long auctionId;
  private final long auctionWinnerId;
  private final long slotId;
}
