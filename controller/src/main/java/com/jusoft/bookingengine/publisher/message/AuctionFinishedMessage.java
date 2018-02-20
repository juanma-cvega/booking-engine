package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;

@Data(staticConstructor = "of")
public class AuctionFinishedMessage implements InfrastructureMessage {

  private final long auctionId;

}
