package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;

public class ReserveSlotForAuctionWinnerUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ReserveSlotForAuctionWinnerUseCase reserveSlotForAuctionWinnerUseCase;

  public ReserveSlotForAuctionWinnerUseCaseStepDefinitions() {
    When("^the slot is reserved for the winner of the auction user (\\d+)$", (Long userId) ->
      reserveSlotForAuctionWinnerUseCase.reserveSlotForAuctionWinner(slotCreated.getId(), userId));
  }
}
