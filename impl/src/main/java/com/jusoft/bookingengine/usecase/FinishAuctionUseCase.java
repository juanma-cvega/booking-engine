package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionStrategyRegistrar;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FinishAuctionUseCase {

  private final RoomComponent roomComponent;
  private final AuctionComponent auctionComponent;
  private final AuctionStrategyRegistrar auctionStrategyRegistrar;

  public void finishAuction(long auctionId, long roomId) {
    RoomView room = roomComponent.find(roomId);
    AuctionWinnerStrategy strategy = auctionStrategyRegistrar.createStrategyWith(room.getAuctionConfigInfo());
    auctionComponent.finishAuction(new FinishAuctionCommand(auctionId, strategy));
  }
}
