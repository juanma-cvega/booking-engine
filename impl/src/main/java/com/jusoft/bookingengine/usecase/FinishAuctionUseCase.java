package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionStrategyRegistrar;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FinishAuctionUseCase {

  private final AuctionComponent auctionComponent;
  private final AuctionStrategyRegistrar auctionStrategyRegistrar;

  public void finishAuction(long auctionId) {
    AuctionView auction = auctionComponent.find(auctionId).orElseThrow(
      () -> new AuctionNotFoundException(auctionId));
    AuctionWinnerStrategy strategy = auctionStrategyRegistrar.createStrategyWith(auction.getAuctionConfigInfo());
    auctionComponent.finishAuction(new FinishAuctionCommand(auctionId, strategy));
  }
}
