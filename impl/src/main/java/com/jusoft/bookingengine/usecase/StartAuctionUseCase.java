package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartAuctionUseCase {

  private final AuctionComponent auctionComponent;

  public AuctionView startAuction(StartAuctionCommand command) {
    return auctionComponent.startAuction(command);
  }
}
