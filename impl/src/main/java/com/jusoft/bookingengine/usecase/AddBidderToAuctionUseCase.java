package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBidderToAuctionUseCase {

  private final AuctionComponent auctionComponent;

  public void addBidderTo(long auctionId, long userId) {
    auctionComponent.addBidderTo(auctionId, userId);
  }
}
