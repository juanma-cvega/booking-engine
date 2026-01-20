package com.jusoft.bookingengine.usecase.booking;

import static com.jusoft.bookingengine.holder.DataHolder.bookingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;

public class CancelBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private BookingManagerComponent bookingManagerComponent;

    @Autowired private CancelBookingUseCase cancelBookingUseCase;

    @Given("^the slot start time is passed$")
    public void the_slot_start_time_is_passed() {
        clock.setClock(
                Clock.fixed(Instant.now().plus(20, ChronoUnit.DAYS), ZoneId.systemDefault()));
    }

    @When("^the user (.*) cancels the booking from user (.*)$")
    public void the_user_cancels_the_booking_from_user(Long userToCancel, Long userOwner) {
        storeException(() -> cancelBookingUseCase.cancel(userToCancel, bookingCreated.id()));
    }

    @When("^the user (.*) cancels his booking$")
    public void the_user_cancels_his_booking(Long userId) {
        cancelBookingUseCase.cancel(userId, bookingCreated.id());
    }

    @When("^the user (\\d+) tries to cancel his booking$")
    public void the_user_tries_to_cancel_his_booking(Long userId) {
        storeException(() -> cancelBookingUseCase.cancel(userId, bookingCreated.id()));
    }

    @Then("^the user (.*) should not see that booking in his list$")
    public void the_user_should_not_see_that_booking_in_his_list(Long userId) {
        assertThatExceptionOfType(BookingNotFoundException.class)
                .isThrownBy(() -> bookingManagerComponent.find(bookingCreated.id()));
    }

    @Then("^the user should be notified the booking is already started$")
    public void the_user_should_be_notified_the_booking_is_already_started() {
        assertThat(exceptionThrown).isNotNull().isInstanceOf(SlotNotOpenException.class);
        SlotNotOpenException exceptionThrown = (SlotNotOpenException) DataHolder.exceptionThrown;
        assertThat(exceptionThrown.getSlotId()).isEqualTo(bookingCreated.slotId());
    }

    @Then("^the user (.*) should be notified the booking does belong to other user$")
    public void the_user_should_be_notified_the_booking_does_belong_to_other_user(Long userId) {
        assertThat(exceptionThrown).isNotNull().isInstanceOf(WrongBookingUserException.class);
        WrongBookingUserException exception = (WrongBookingUserException) exceptionThrown;
        assertThat(exception.getBookingId()).isEqualTo(bookingCreated.id());
        assertThat(exception.getExpectedId()).isEqualTo(bookingCreated.userId());
        assertThat(exception.getProvidedId()).isEqualTo(userId);
    }
}
