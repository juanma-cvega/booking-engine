package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBuyerToAuctionUseCase {

  private final AuctionComponent auctionComponent;

  public void addBuyerTo(long slotId, long userId) {
    auctionComponent.addBuyerTo(slotId, userId);
  }
}
