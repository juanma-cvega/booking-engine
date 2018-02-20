package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionStrategyRegistrar;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FinishAuctionUseCase {

  private final AuctionManagerComponent auctionManagerComponent;
  private final AuctionStrategyRegistrar auctionStrategyRegistrar;

  public void finishAuction(long auctionId) {
    AuctionView auction = auctionManagerComponent.find(auctionId);
    AuctionWinnerStrategy strategy = auctionStrategyRegistrar.createStrategyWith(auction.getAuctionConfigInfo());
    auctionManagerComponent.finishAuction(auctionId, strategy);
  }
}
