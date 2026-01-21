package com.jusoft.bookingengine.usecase.auction;

import static com.jusoft.bookingengine.holder.DataHolder.auctionCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verifyNoInteractions;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionNotFoundException;
import com.jusoft.bookingengine.component.auction.api.AuctionStartedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StartAuctionUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private AuctionManagerComponent auctionManagerComponent;

    @Autowired private StartAuctionUseCase startAuctionUseCase;

    @When(
            "^an auction is created for the slot with a (.*) minutes auction time and a (.*) days bookings created window$")
    public void
            an_auction_is_created_for_the_slot_with_a_minutes_auction_time_and_a_days_bookings_created_window(
                    Integer auctionDuration, Integer daysRange) {
        auctionCreated =
                startAuctionUseCase.startAuction(
                        StartAuctionCommand.of(
                                slotCreated.id(),
                                LessBookingsWithinPeriodConfigInfo.of(auctionDuration, daysRange)));
    }

    @Then("^the auction should be stored$")
    public void the_auction_should_be_store() {
        AuctionView auction = auctionManagerComponent.find(DataHolder.auctionCreated.id());
        assertThat(auction.bidders()).hasSameElementsAs(DataHolder.auctionCreated.bidders());
        assertThat(auction.openDate().getEndTime())
                .isEqualTo(DataHolder.auctionCreated.openDate().getEndTime());
        assertThat(auction.referenceId()).isEqualTo(DataHolder.auctionCreated.referenceId());
        assertThat(auction.openDate().getStartTime())
                .isEqualTo(DataHolder.auctionCreated.openDate().getStartTime());
    }

    @Then("^the auction shouldn't exist$")
    public void the_auction_should_not_exist() {
        assertThat(auctionCreated).isNull();
        assertThatThrownBy(() -> auctionManagerComponent.addBidderToAuctionFor(slotCreated.id(), 0))
                .isInstanceOf(AuctionNotFoundException.class);
    }

    @Then("^an auction finished event shouldn't be scheduled to be published$")
    public void an_auction_finished_event_should_not_be_scheduled_to_be_published() {
        await().with()
                .pollDelay(1, SECONDS)
                .untilAsserted(() -> verifyNoInteractions(messagePublisher));
    }

    @Then("^an auction started event should be published$")
    public void an_auction_started_event_should_be_published() {
        AuctionStartedEvent event = verifyAndGetMessageOfType(AuctionStartedEvent.class);
        assertThat(event.auctionId()).isEqualTo(auctionCreated.id());
        assertThat(event.openDate()).isEqualTo(auctionCreated.openDate());
        assertThat(event.referenceId()).isEqualTo(auctionCreated.referenceId());
    }
}
