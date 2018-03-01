package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScheduleFinishAuctionUseCase {

  private final SchedulerComponent schedulerComponent;

  public void scheduleFinishAuction(long auctionId, OpenDate openDate) {
    schedulerComponent.schedule(openDate.getEndTime(), AuctionFinishedEvent.of(auctionId));
  }
}
