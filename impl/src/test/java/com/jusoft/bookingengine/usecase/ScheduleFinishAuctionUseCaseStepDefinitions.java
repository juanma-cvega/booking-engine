package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleFinishAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private List<ScheduledTask> tasks;

  @Autowired
  private ScheduleFinishAuctionUseCase scheduleFinishAuctionUseCase;

  public ScheduleFinishAuctionUseCaseStepDefinitions() {
    Then("^an auction finished event should be scheduled to be published at (.*)$", (String auctionFinishedTime) -> {
      assertThat(tasks).hasSize(1);
      assertThat(tasks.get(0).getExecutionTime()).isEqualTo(getDateFrom(auctionFinishedTime));
      assertThat(tasks.get(0).getScheduledEvent()).isEqualTo(
        AuctionFinishedEvent.of(auctionCreated.getId()));
      assertThat(tasks.get(0).getTask().isDone()).isFalse();
    });
    When("^the auction is scheduled to finish$", () ->
      scheduleFinishAuctionUseCase.scheduleFinishAuction(auctionCreated.getId(), auctionCreated.getOpenDate()));

  }
}
