package com.jusoft.bookingengine.usecase.booking;

import static com.jusoft.bookingengine.holder.DataHolder.bookingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.bookingFound;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class FindBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private FindBookingUseCase findBookingUseCase;

    @When("^user (\\d+) asks for his booking$")
    public void user_asks_for_his_booking(Long userId) {
        bookingFound = findBookingUseCase.find(userId, bookingCreated.id());
    }

    @When("^an unauthorized user (\\d+) asks for the booking from user (\\d+)$")
    public void an_unauthorized_user_asks_for_the_booking_from_user(
            Long unauthorizedUserId, Long ownerUserId) {
        storeException(() -> findBookingUseCase.find(unauthorizedUserId, bookingCreated.id()));
    }

    @Then("^user (\\d+) should see the booking he created$")
    public void user_should_see_the_booking_he_created(Long userId) {
        assertThat(bookingFound).isNotNull();
        assertThat(bookingFound.id()).isEqualTo(bookingCreated.id());
        assertThat(bookingFound.userId()).isEqualTo(userId);
        assertThat(bookingFound.slotId()).isEqualTo(bookingCreated.slotId());
    }
}
