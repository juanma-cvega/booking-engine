package com.jusoft.bookingengine.usecase.room;

import com.jusoft.bookingengine.component.room.api.AuctionRequiredEvent;
import com.jusoft.bookingengine.component.room.api.SlotReadyEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class VerifyAuctionRequirementForSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private VerifyAuctionRequirementForSlotUseCase verifyAuctionRequirementForSlotUseCase;

  public VerifyAuctionRequirementForSlotUseCaseStepDefinitions() {
    When("^the room is asked whether the slot needs an auction$", () ->
      verifyAuctionRequirementForSlotUseCase.isAuctionRequiredFor(roomCreated.getId(), slotCreated.getId()));
    Then("^an event of an auction required should be published$", () -> {
      AuctionRequiredEvent event = verifyAndGetMessageOfType(AuctionRequiredEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotCreated.getId());
      assertThat(event.getAuctionConfigInfo()).isEqualTo(roomCreated.getAuctionConfigInfo());
    });
    Then("^an event of the slot being ready should be published$", () -> {
      SlotReadyEvent event = verifyAndGetMessageOfType(SlotReadyEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotCreated.getId());
    });
  }
}
