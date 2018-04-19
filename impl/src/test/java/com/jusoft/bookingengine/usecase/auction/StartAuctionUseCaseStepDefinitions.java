package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
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

  public StartAuctionUseCaseStepDefinitions() {
    When("^an auction is created for the slot with a (.*) minutes auction time and a (.*) days bookings created window$", (Integer auctionDuration, Integer daysRange) ->
      auctionCreated = startAuctionUseCase.startAuction(StartAuctionCommand.of(slotCreated.getId(), LessBookingsWithinPeriodConfigInfo.of(auctionDuration, daysRange))));
    Then("^the auction should be stored$", () -> {
      AuctionView auction = auctionManagerComponent.find(DataHolder.auctionCreated.getId());
      assertThat(auction.getBidders()).hasSameElementsAs(DataHolder.auctionCreated.getBidders());
      assertThat(auction.getOpenDate().getEndTime()).isEqualTo(DataHolder.auctionCreated.getOpenDate().getEndTime());
      assertThat(auction.getReferenceId()).isEqualTo(DataHolder.auctionCreated.getReferenceId());
      assertThat(auction.getOpenDate().getStartTime()).isEqualTo(DataHolder.auctionCreated.getOpenDate().getStartTime());
    });
    Then("^the auction shouldn't exist$", () -> {
      assertThat(auctionCreated).isNull();
      assertThatThrownBy(() -> auctionManagerComponent.addBidderToAuctionFor(slotCreated.getId(), 0))
        .isInstanceOf(AuctionNotFoundException.class);
    });
    Then("^an auction finished event shouldn't be scheduled to be published$", () ->
      await().with().pollDelay(1, SECONDS).untilAsserted(() -> verifyZeroInteractions(messagePublisher)));
    Then("^an auction started event should be published$", () -> {
      AuctionStartedEvent event = verifyAndGetMessageOfType(AuctionStartedEvent.class);
      assertThat(event.getAuctionId()).isEqualTo(auctionCreated.getId());
      assertThat(event.getOpenDate()).isEqualTo(auctionCreated.getOpenDate());
      assertThat(event.getReferenceId()).isEqualTo(auctionCreated.getReferenceId());
    });
  }
}
