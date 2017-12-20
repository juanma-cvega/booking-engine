package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedException;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AddBuyerToAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuctionComponent auctionComponent;
  @Autowired
  private AddBuyerToAuctionUseCase addBuyerToAuctionUseCase;

  public AddBuyerToAuctionUseCaseStepDefinitions() {
    When("^user (.*) bets on the auction$", (Integer userId) ->
      storeException(() -> addBuyerToAuctionUseCase.addBuyerTo(slotHolder.slotCreated.getId(), userId)));
    Then("^the auction should contain the user (\\d+) bet created at (.*)$", (Integer userId, String creationTime) -> {
      Optional<AuctionView> auctionFound = auctionComponent.find(auctionHolder.auctionCreated.getId());
      assertThat(auctionFound).isPresent();
      AuctionView auction = auctionFound.get();
      assertThat(auction.getBuyers()).contains(new Bid(userId, getDateFrom(creationTime)));
    });
    Then("^the user should be notified the auction is finished$", () -> {
      assertThat(exceptionHolder.exceptionThrown).isInstanceOf(AuctionFinishedException.class);
      AuctionFinishedException exception = (AuctionFinishedException) exceptionHolder.exceptionThrown;
      assertThat(exception.getAuctionId()).isEqualTo(auctionHolder.auctionCreated.getId());
      assertThat(exception.getSlotId()).isEqualTo(auctionHolder.auctionCreated.getSlotId());
      assertThat(exception.getRoomId()).isEqualTo(auctionHolder.auctionCreated.getRoomId());
    });
  }
}
