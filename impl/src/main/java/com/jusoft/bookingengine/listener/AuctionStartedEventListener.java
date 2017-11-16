package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AuctionStartedEventListener implements MessageListener<AuctionStartedEvent> {

  private final SchedulerComponent schedulerComponent;

  @Override
  public void consume(AuctionStartedEvent event) {
    log.info("AuctionStartedEvent consumed: auctionId={}, endTime={}, slotId={}",
      event.getAuctionId(), event.getAuctionEndTime(), event.getSlotId());
    schedulerComponent.schedule(taskBuilder -> {
      taskBuilder.event(new AuctionFinishedEvent(event.getAuctionId()));
      taskBuilder.executionTime(event.getAuctionEndTime());
    });
  }
}
