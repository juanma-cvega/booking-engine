package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.usecase.api.ScheduleFinishAuctionUseCaseInfo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScheduleFinishAuctionUseCase {

  private final SchedulerComponent schedulerComponent;

  public void scheduleFinishAuction(ScheduleFinishAuctionUseCaseInfo useCaseInfo) {
    schedulerComponent.schedule(useCaseInfo.getOpenDate().getEndTime(), AuctionFinishedEvent.of(useCaseInfo.getAuctionId()));
  }
}
