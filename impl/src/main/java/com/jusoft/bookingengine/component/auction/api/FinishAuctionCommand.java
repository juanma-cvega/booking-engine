package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import lombok.Data;

@Data
public class FinishAuctionCommand {

  private final long auctionId;
  private final AuctionConfigInfo auctionConfigInfo;
}
