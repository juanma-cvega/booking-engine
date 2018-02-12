package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.component.room.api.VerifyAuctionRequirementForSlotCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;

public class VerifyAuctionRequirementForSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private VerifyAuctionRequirementForSlotUseCase verifyAuctionRequirementForSlotUseCase;

  public VerifyAuctionRequirementForSlotUseCaseStepDefinitions() {
    When("^the room is asked whether the slot needs an auction$", () ->
      verifyAuctionRequirementForSlotUseCase.isAuctionRequiredForSlot(VerifyAuctionRequirementForSlotCommand.of(roomCreated.getId(), slotCreated.getId())));
    Then("^a request to create an auction should be published$", () -> {
      StartAuctionCommand command = verifyAndGetMessageOfType(StartAuctionCommand.class);
      assertThat(command.getReferenceId()).isEqualTo(slotCreated.getId());
      assertThat(command.getAuctionConfigInfo()).isEqualTo(roomCreated.getAuctionConfigInfo());
    });
    Then("^a request to create an auction should not be published$", () ->
      verifyZeroInteractions(messagePublisher));
  }
}
