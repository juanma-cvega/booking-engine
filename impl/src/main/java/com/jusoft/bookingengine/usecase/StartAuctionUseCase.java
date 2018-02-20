package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartAuctionUseCase {

  private final AuctionManagerComponent auctionManagerComponent;

  public AuctionView startAuction(StartAuctionCommand command) {
    return auctionManagerComponent.startAuction(command);
  }
}
