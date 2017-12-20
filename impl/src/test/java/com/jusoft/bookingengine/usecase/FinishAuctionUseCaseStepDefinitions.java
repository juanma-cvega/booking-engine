package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class FinishAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private FinishAuctionUseCase finishAuctionUseCase;

  public FinishAuctionUseCaseStepDefinitions() {

    When("^the auction time is finished at (.*)$", (String currentTime) ->
      finishAuctionUseCase.finishAuction(auctionHolder.auctionCreated.getId(), auctionHolder.auctionCreated.getRoomId()));
    Then("^a notification containing user (\\d+) as the winner should be published$", (Long userId) -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(AuctionWinnerFoundEvent.class);
      AuctionWinnerFoundEvent auctionWinnerFoundEvent = (AuctionWinnerFoundEvent) messageCaptor.getValue();
      assertThat(auctionWinnerFoundEvent.getAuctionWinnerId()).isEqualTo(userId);
      assertThat(auctionWinnerFoundEvent.getRoomId()).isEqualTo(roomHolder.roomCreated.getId());
      assertThat(auctionWinnerFoundEvent.getSlotId()).isEqualTo(slotHolder.slotCreated.getId());
      assertThat(auctionWinnerFoundEvent.getAuctionId()).isEqualTo(auctionHolder.auctionCreated.getId());
    });
    Then("^a notification containing the winner shouldn't be published$", () -> {
      verifyZeroInteractions(messagePublisher);
    });
  }
}
