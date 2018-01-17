package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.SlotNotInAuctionException;
import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verifyZeroInteractions;

public class StartAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuctionComponent auctionComponent;
  @Autowired
  private List<ScheduledTask> tasks;

  @Autowired
  private StartAuctionUseCase startAuctionUseCase;

  public StartAuctionUseCaseStepDefinitions() {
    When("^an auction is created for the slot$", () ->
      auctionCreated = startAuctionUseCase.startAuction(roomCreated.getId(), slotCreated.getId())
        .orElse(null));
    Then("^the auction should be stored$", () -> {
      Optional<AuctionView> auctionCreated = auctionComponent.find(DataHolder.auctionCreated.getId());
      assertThat(auctionCreated).isPresent();
      AuctionView auction = auctionCreated.get();
      assertThat(auction.getBuyers()).hasSameElementsAs(DataHolder.auctionCreated.getBuyers());
      assertThat(auction.getEndTime()).isEqualTo(DataHolder.auctionCreated.getEndTime());
      assertThat(auction.getRoomId()).isEqualTo(DataHolder.auctionCreated.getRoomId());
      assertThat(auction.getSlotId()).isEqualTo(DataHolder.auctionCreated.getSlotId());
      assertThat(auction.getStartTime()).isEqualTo(DataHolder.auctionCreated.getStartTime());
    });
    Then("^an auction finished event should be scheduled to be published at (.*)$", (String auctionFinishedTime) -> {
      assertThat(tasks).hasSize(1);
      assertThat(tasks.get(0).getExecutionTime()).isEqualTo(getDateFrom(auctionFinishedTime));
      assertThat(tasks.get(0).getScheduledEvent()).isEqualTo(
        new AuctionFinishedEvent(auctionCreated.getId(), roomCreated.getId(), slotCreated.getId()));
      assertThat(tasks.get(0).getTask().isDone()).isFalse();
    });
    Then("^the auction shouldn't exist$", () -> {
      assertThat(auctionCreated).isNull();
      assertThatThrownBy(() -> auctionComponent.addBuyerTo(slotCreated.getId(), 0))
        .isInstanceOf(SlotNotInAuctionException.class);
    });
    Then("^an auction finished event shouldn't be scheduled to be published$", () ->
      await().with().pollDelay(1, SECONDS).untilAsserted(() -> verifyZeroInteractions(messagePublisher)));
  }
}
