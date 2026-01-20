package com.jusoft.bookingengine.usecase.slotlifecycle;

import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class ModifyAuctionConfigUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    @Autowired private ModifyAuctionConfigUseCase modifyAuctionConfigUseCase;

    @When(
            "^an auction configuration of (\\d+) minutes duration and bookings period of (\\d+) days is added to slot lifecycle manager for room (\\d+)$")
    public void
            an_auction_configuration_of_minutes_duration_and_bookings_period_of_days_is_added_to_slot_lifecycle_manager_for_room(
                    Integer auctionDuration, Integer periodValue, Long roomId) {
        modifyAuctionConfigUseCase.modifyAuctionConfigFor(
                roomId, LessBookingsWithinPeriodConfigInfo.of(auctionDuration, periodValue));
    }

    @Then(
            "^the slot lifecycle manager for room (\\d+) should contain the auction configuration of (\\d+) minutes duration and bookings period of (\\d+) days$")
    public void
            the_slot_lifecycle_manager_for_room_should_contain_the_auction_configuration_of_minutes_duration_and_bookings_period_of_days(
                    Long roomId, Integer auctionDuration, Integer periodValue) {
        SlotLifeCycleManagerView slotLifeCycleManager = slotLifeCycleManagerComponent.find(roomId);
        assertThat(slotLifeCycleManager.getAuctionConfigInfo())
                .isInstanceOf(LessBookingsWithinPeriodConfigInfo.class);
        LessBookingsWithinPeriodConfigInfo auctionConfig =
                (LessBookingsWithinPeriodConfigInfo) slotLifeCycleManager.getAuctionConfigInfo();
        assertThat(auctionConfig.getAuctionDuration()).isEqualTo(auctionDuration);
        assertThat(auctionConfig.getEndRangeTimeInDays()).isEqualTo(periodValue);
    }
}
