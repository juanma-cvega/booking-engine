package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import lombok.Data;
import lombok.NonNull;

@Data
public class FinishAuctionCommand {

  private final long auctionId;
  @NonNull
  private final AuctionWinnerStrategy auctionWinnerStrategy;
}
