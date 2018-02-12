package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedException;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;

public class AddBidderToAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuctionComponent auctionComponent;
  @Autowired
  private AddBidderToAuctionUseCase addBidderToAuctionUseCase;

  public AddBidderToAuctionUseCaseStepDefinitions() {
    When("^user (.*) bids on the auction$", (Long userId) ->
      addBidderToAuctionUseCase.addBidderTo(auctionCreated.getId(), userId));
    When("^user (.*) tries to bid on the auction$", (Long userId) ->
      storeException(() -> addBidderToAuctionUseCase.addBidderTo(auctionCreated.getId(), userId)));
    Then("^the auction should contain the user (\\d+) bid created at (.*)$", (Integer userId, String creationTime) -> {
      Optional<AuctionView> auctionFound = auctionComponent.find(auctionCreated.getId());
      assertThat(auctionFound).isPresent();
      AuctionView auction = auctionFound.get();
      assertThat(auction.getBidders()).contains(new Bid(userId, getDateFrom(creationTime)));
    });
    Then("^the user should be notified the auction is finished$", () -> {
      assertThat(exceptionThrown).isInstanceOf(AuctionFinishedException.class);
      AuctionFinishedException exception = (AuctionFinishedException) exceptionThrown;
      assertThat(exception.getAuctionId()).isEqualTo(auctionCreated.getId());
      assertThat(exception.getSlotId()).isEqualTo(auctionCreated.getReferenceId());
    });
  }
}
