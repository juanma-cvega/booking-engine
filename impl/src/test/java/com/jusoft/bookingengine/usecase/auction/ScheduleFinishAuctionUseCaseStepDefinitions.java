package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleFinishAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private List<ScheduledTask> tasks;

  @Autowired
  private ScheduleFinishAuctionUseCase scheduleFinishAuctionUseCase;

  @Then("^an auction finished event should be scheduled to be published at (.*)$")
  public void an_auction_finished_event_should_be_scheduled_to_be_published_at(String auctionFinishedTime) {
    assertThat(tasks).hasSize(1);
    assertThat(tasks.get(0).getExecutionTime()).isEqualTo(getDateFrom(auctionFinishedTime));
    assertThat(tasks.get(0).getScheduledEvent()).isEqualTo(AuctionFinishedEvent.of(auctionCreated.getId()));
    assertThat(tasks.get(0).getTask().isDone()).isFalse();
  }
  @When("^the auction is scheduled to finish$")
  public void the_auction_is_scheduled_to_finish() {
    scheduleFinishAuctionUseCase.scheduleFinishAuction(auctionCreated.getId(), auctionCreated.getOpenDate());
  }
}
