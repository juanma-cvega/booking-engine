package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.slot.api.SlotWaitingForAuctionEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.component.slot.api.SlotState.IN_AUCTION;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class MakeSlotWaitForAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotManagerComponent slotManagerComponent;
  @Autowired
  private MakeSlotWaitForAuctionUseCase makeSlotWaitForAuctionUseCase;

  public MakeSlotWaitForAuctionUseCaseStepDefinitions() {
    When("^the slot is made wait for the result of an auction$", () ->
      makeSlotWaitForAuctionUseCase.makeSlotWaitForAuction(slotCreated.getId()));
    Then("^the slot should wait for the result of the auction$", () -> {
      SlotView slot = slotManagerComponent.find(slotCreated.getId());
      assertThat(slot.getState()).isEqualTo(IN_AUCTION);
    });
    Then("^a notification the slot is waiting for the result of the auction should be published$", () -> {
      SlotWaitingForAuctionEvent event = verifyAndGetMessageOfType(SlotWaitingForAuctionEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotCreated.getId());
    });
  }
}
