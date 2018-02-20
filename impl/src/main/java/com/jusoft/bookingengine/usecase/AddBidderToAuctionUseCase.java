package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBidderToAuctionUseCase {

  private final AuctionManagerComponent auctionManagerComponent;

  public void addBidderTo(long auctionId, long userId) {
    auctionManagerComponent.addBidderTo(auctionId, userId);
  }
}
