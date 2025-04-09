package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verifyZeroInteractions;

public class StartAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuctionManagerComponent auctionManagerComponent;

  @Autowired
  private StartAuctionUseCase startAuctionUseCase;

  @When("^an auction is created for the slot with a (.*) minutes auction time and a (.*) days bookings created window$")
  public void an_auction_is_created_for_the_slot_with_a_minutes_auction_time_and_a_days_bookings_created_window(Integer auctionDuration, Integer daysRange){
      auctionCreated = startAuctionUseCase.startAuction(StartAuctionCommand.of(slotCreated.getId(), LessBookingsWithinPeriodConfigInfo.of(auctionDuration, daysRange)));
  }

  @Then("^the auction should be stored$")
  public void the_auction_should_be_store() {
    AuctionView auction = auctionManagerComponent.find(DataHolder.auctionCreated.getId());
    assertThat(auction.getBidders()).hasSameElementsAs(DataHolder.auctionCreated.getBidders());
    assertThat(auction.getOpenDate().getEndTime()).isEqualTo(DataHolder.auctionCreated.getOpenDate().getEndTime());
    assertThat(auction.getReferenceId()).isEqualTo(DataHolder.auctionCreated.getReferenceId());
    assertThat(auction.getOpenDate().getStartTime()).isEqualTo(DataHolder.auctionCreated.getOpenDate().getStartTime());
  }

  @Then("^the auction shouldn't exist$")
  public void the_auction_should_not_exist() {
    assertThat(auctionCreated).isNull();
    assertThatThrownBy(() -> auctionManagerComponent.addBidderToAuctionFor(slotCreated.getId(), 0))
      .isInstanceOf(AuctionNotFoundException.class);
  }

  @Then("^an auction finished event shouldn't be scheduled to be published$")
  public void an_auction_finished_event_should_not_be_scheduled_to_be_published() {
    await().with().pollDelay(1, SECONDS).untilAsserted(() -> verifyZeroInteractions(messagePublisher));
  }

  @Then("^an auction started event should be published$")
  public void an_auction_started_event_should_be_published()  {
    AuctionStartedEvent event = verifyAndGetMessageOfType(AuctionStartedEvent.class);
    assertThat(event.getAuctionId()).isEqualTo(auctionCreated.getId());
    assertThat(event.getOpenDate()).isEqualTo(auctionCreated.getOpenDate());
    assertThat(event.getReferenceId()).isEqualTo(auctionCreated.getReferenceId());
  }
}
