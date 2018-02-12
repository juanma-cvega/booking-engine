package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static com.jusoft.bookingengine.holder.DataHolder.bookingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CancelBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private BookingComponent bookingComponent;

  @Autowired
  private CancelBookingUseCase cancelBookingUseCase;

  public CancelBookingUseCaseStepDefinitions() {
    Given("^the slot start time is passed$", () ->
      clock.setClock(Clock.fixed(Instant.now().plus(20, ChronoUnit.DAYS), ZoneId.systemDefault())));
    When("^the user (.*) cancels the booking from user (.*)$", (Long userToCancel, Long userOwner) ->
      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userToCancel, bookingCreated.getId()))));
    When("^the user (.*) cancels his booking$", (Long userId) ->
      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userId, bookingCreated.getId()))));
    When("^user (.*) cancels the booking$", (Long userId) ->
      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userId, bookingCreated.getId()))));
    Then("^the user (.*) should not see that booking in his list$", (Long userId) ->
      assertThatExceptionOfType(BookingNotFoundException.class)
        .isThrownBy(() -> bookingComponent.find(userId, bookingCreated.getId())));
    Then("^the user should be notified the booking is already started$", () -> {
      assertThat(exceptionThrown).isNotNull().isInstanceOf(SlotNotAvailableException.class);
      SlotNotAvailableException exceptionThrown = (SlotNotAvailableException) DataHolder.exceptionThrown;
      assertThat(exceptionThrown.getSlotId()).isEqualTo(bookingCreated.getSlotId());
    });
    Then("^the user (.*) should be notified the booking does belong to other user$", (Long userId) -> {
      assertThat(exceptionThrown).isNotNull().isInstanceOf(WrongBookingUserException.class);
      WrongBookingUserException exception = (WrongBookingUserException) exceptionThrown;
      assertThat(exception.getBookingId()).isEqualTo(bookingCreated.getId());
      assertThat(exception.getExpectedId()).isEqualTo(bookingCreated.getUserId());
      assertThat(exception.getProvidedId()).isEqualTo(userId);
    });
  }
}
