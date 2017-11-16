package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.shared.Message;
import lombok.Data;

@Data
public class AuctionWinnerFoundEvent implements Message {

  private final long auctionId;
  private final long auctionWinnerId;
  private final long slotId;
  private final long roomId;
}
