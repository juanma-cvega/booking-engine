package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionUnsuccessfulEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class FinishAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private FinishAuctionUseCase finishAuctionUseCase;

  public FinishAuctionUseCaseStepDefinitions() {

    When("^the auction time is finished$", () ->
      finishAuctionUseCase.finishAuction(auctionCreated.getId()));
    Then("^a notification containing user (\\d+) as the winner should be published$", (Long userId) -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(AuctionWinnerFoundEvent.class);
      AuctionWinnerFoundEvent auctionWinnerFoundEvent = (AuctionWinnerFoundEvent) messageCaptor.getValue();
      assertThat(userId).isEqualTo(auctionWinnerFoundEvent.getAuctionWinnerId());
      assertThat(slotCreated.getId()).isEqualTo(auctionWinnerFoundEvent.getSlotId());
      assertThat(auctionCreated.getId()).isEqualTo(auctionWinnerFoundEvent.getAuctionId());
    });
    Then("^a notification saying the slot can be made available should be published$", () -> {
      AuctionUnsuccessfulEvent command = verifyAndGetMessageOfType(AuctionUnsuccessfulEvent.class);
      assertThat(command.getReferenceId()).isEqualTo(slotCreated.getId());
    });
  }
}
