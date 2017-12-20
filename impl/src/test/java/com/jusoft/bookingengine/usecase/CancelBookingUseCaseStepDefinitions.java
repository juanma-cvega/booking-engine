package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CancelBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private BookingComponent bookingComponent;

  @Autowired
  private CancelBookingUseCase cancelBookingUseCase;

  public CancelBookingUseCaseStepDefinitions() {
    When("^the user (.*) cancels the booking from user (.*)$", (Long userToCancel, Long userOwner) ->
      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userToCancel, bookingHolder.bookingCreated.getId()))));
    When("^the user (.*) cancels his booking$", (Long userId) ->
      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userId, bookingHolder.bookingCreated.getId()))));
    When("^user (.*) cancels the booking$", (Long userId) ->
      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userId, bookingHolder.bookingCreated.getId()))));
    Then("^the user (.*) should not see that booking in his list$", (Long userId) ->
      assertThatExceptionOfType(BookingNotFoundException.class)
        .isThrownBy(() -> bookingComponent.find(userId, bookingHolder.bookingCreated.getId())));
    Then("^the user should be notified the booking is already started$", () -> {
      assertThat(exceptionHolder.exceptionThrown).isNotNull().isInstanceOf(SlotAlreadyStartedException.class);
      SlotAlreadyStartedException exceptionThrown = (SlotAlreadyStartedException) exceptionHolder.exceptionThrown;
      assertThat(exceptionThrown.getSlotId()).isEqualTo(bookingHolder.bookingCreated.getSlotId());
    });

  }
}
