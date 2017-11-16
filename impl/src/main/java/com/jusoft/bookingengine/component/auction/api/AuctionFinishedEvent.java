package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.shared.Message;
import lombok.Data;

@Data
public class AuctionFinishedEvent implements Message {

  private final long auctionId;
}
