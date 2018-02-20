package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Command;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class FinishAuctionCommand implements Command {

  private final long auctionId;
  @NonNull
  private final AuctionWinnerStrategy auctionWinnerStrategy;
}
