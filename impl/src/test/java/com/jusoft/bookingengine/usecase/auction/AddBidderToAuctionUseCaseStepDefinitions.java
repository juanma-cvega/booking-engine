package com.jusoft.bookingengine.usecase.auction;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedException;
import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ChangeAccessToAuctionsCommand;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedBidException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class AddBidderToAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private AuctionManagerComponent auctionManagerComponent;

    @Autowired private AuthorizationManagerComponent authorizationManagerComponent;

    @Autowired private AddBidderToAuctionUseCase addBidderToAuctionUseCase;

    @When("^user (.*) bids on the auction$")
    public void user_bids_on_the_auction(Long userId) {
        addBidderToAuctionUseCase.addBidderToAuctionFor(userId, auctionCreated.getReferenceId());
    }

    @When("^user (.*) tries to bid on the auction$")
    public void user_tries_to_bid_on_the_auction(Long userId) {
        storeException(
                () -> addBidderToAuctionUseCase.addBidderToAuctionFor(userId, slotCreated.id()));
    }

    @Then("^the auction should contain the user (\\d+) bid created at (.*)$")
    public void the_auction_should_contain_the_user_bid_created_at(
            Integer userId, String creationTime) {
        AuctionView auction = auctionManagerComponent.find(auctionCreated.getId());
        assertThat(auction.getBidders()).contains(new Bid(userId, getDateFrom(creationTime)));
    }

    @Then("^the user should be notified the auction is finished$")
    public void the_user_should_be_notified_the_auction_is_finished() {
        assertThat(exceptionThrown).isInstanceOf(AuctionFinishedException.class);
        AuctionFinishedException exception = (AuctionFinishedException) exceptionThrown;
        assertThat(exception.getAuctionId()).isEqualTo(auctionCreated.getId());
        assertThat(exception.getSlotId()).isEqualTo(auctionCreated.getReferenceId());
    }
    ;

    @Given("^member (\\d+) can bid in auctions$")
    public void member_can_bid_in_auctions(Long memberId) {
        authorizationManagerComponent.addAccessToAuctions(
                ChangeAccessToAuctionsCommand.of(
                        memberId, buildingCreated.getId(), roomCreated.getId()));
    }

    @Then(
            "^the user (.*) should be notified he is not authorized to bid in auctions in the room created$")
    public void
            the_user_should_be_notified_he_is_not_authorized_to_bid_in_auctions_in_the_room_created(
                    Long userId) {
        assertThat(exceptionThrown).isInstanceOf(UnauthorizedBidException.class);
        UnauthorizedBidException exception = (UnauthorizedBidException) exceptionThrown;
        assertThat(exception.getBuildingId()).isEqualTo(roomCreated.getBuildingId());
        assertThat(exception.getClubId()).isEqualTo(roomCreated.getClubId());
        assertThat(exception.getRoomId()).isEqualTo(roomCreated.getId());
        assertThat(exception.getUserId()).isEqualTo(userId);
    }

    @Given("^member (.*) is not authorized to bid in auctions for the room$")
    public void member_is_not_authorized_to_bid_in_auctions_for_the_room(Long memberId) {
        authorizationManagerComponent.removeAccessToAuctions(
                ChangeAccessToAuctionsCommand.of(
                        memberId, roomCreated.getBuildingId(), roomCreated.getId()));
    }
}
