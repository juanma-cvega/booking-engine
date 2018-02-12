package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartAuctionUseCase {

  private final AuctionComponent auctionComponent;
  private final SchedulerComponent schedulerComponent;

  public AuctionView startAuction(StartAuctionCommand command) {
    AuctionView newAuction = auctionComponent.startAuction(command);
    AuctionFinishedEvent message = new AuctionFinishedEvent(newAuction.getId(), newAuction.getReferenceId());
    schedulerComponent.schedule(newAuction.getOpenDate().getEndTime(), message);
    return newAuction;
  }
}
