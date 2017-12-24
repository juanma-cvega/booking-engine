package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import lombok.Data;
import lombok.NonNull;

@Data
public class FinishAuctionCommand {

  private final long auctionId;
  @NonNull
  private final AuctionConfigInfo auctionConfigInfo;
}
