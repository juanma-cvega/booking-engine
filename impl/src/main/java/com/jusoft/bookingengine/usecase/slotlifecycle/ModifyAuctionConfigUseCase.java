package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModifyAuctionConfigUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void modifyAuctionConfigFor(long roomId, AuctionConfigInfo auctionConfigInfo) {
    slotLifeCycleManagerComponent.modifyAuctionConfigFor(roomId, auctionConfigInfo);
  }
}
