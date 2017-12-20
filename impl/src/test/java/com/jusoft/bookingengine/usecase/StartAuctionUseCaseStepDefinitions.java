package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.SlotNotInAuctionException;
import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class StartAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuctionComponent auctionComponent;

  @Autowired
  private StartAuctionUseCase startAuctionUseCase;

  public StartAuctionUseCaseStepDefinitions() {
    When("^an auction is created for the slot$", () ->
      auctionHolder.auctionCreated = startAuctionUseCase.startAuction(roomHolder.roomCreated.getId(), slotHolder.slotCreated.getId())
        .orElse(null));
    Then("^the auction should be stored$", () -> {
      Optional<AuctionView> auctionCreated = auctionComponent.find(auctionHolder.auctionCreated.getId());
      assertThat(auctionCreated).isPresent();
      AuctionView auction = auctionCreated.get();
      assertThat(auction.getBuyers()).hasSameElementsAs(auctionHolder.auctionCreated.getBuyers());
      assertThat(auction.getEndTime()).isEqualTo(auctionHolder.auctionCreated.getEndTime());
      assertThat(auction.getRoomId()).isEqualTo(auctionHolder.auctionCreated.getRoomId());
      assertThat(auction.getSlotId()).isEqualTo(auctionHolder.auctionCreated.getSlotId());
      assertThat(auction.getStartTime()).isEqualTo(auctionHolder.auctionCreated.getStartTime());
    });
    Then("^an auction finished event should be scheduled to be published at (.*)$", (String auctionFinishedTime) -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(ScheduledEvent.class);
      ScheduledEvent scheduledEvent = (ScheduledEvent) messageCaptor.getValue();
      assertThat(scheduledEvent.getMessage()).isInstanceOf(AuctionFinishedEvent.class);
      AuctionFinishedEvent auctionFinishedEvent = (AuctionFinishedEvent) scheduledEvent.getMessage();
      ZonedDateTime finishAuctionDate = getDateFrom(auctionFinishedTime);
      assertThat(auctionFinishedEvent.getAuctionId()).isEqualTo(auctionHolder.auctionCreated.getId());
      assertThat(auctionFinishedEvent.getRoomId()).isEqualTo(auctionHolder.auctionCreated.getRoomId());
      assertThat(auctionFinishedEvent.getSlotId()).isEqualTo(auctionHolder.auctionCreated.getSlotId());
      assertThat(scheduledEvent.getExecutionTime()).isEqualTo(finishAuctionDate);
    });
    Then("^the auction shouldn't exist$", () -> {
      assertThat(auctionHolder.auctionCreated).isNull();
      assertThatThrownBy(() -> auctionComponent.addBuyerTo(slotHolder.slotCreated.getId(), 0))
        .isInstanceOf(SlotNotInAuctionException.class);
    });
    Then("^an auction finished event shouldn't be scheduled to be published$", () ->
      verifyZeroInteractions(messagePublisher));
  }
}
