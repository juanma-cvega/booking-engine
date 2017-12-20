package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyBookedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private BookingComponent bookingComponent;
  @Autowired
  private SlotComponent slotComponent;

  @Autowired
  private CreateBookingUseCase createBookingUseCase;

  public CreateBookingUseCaseStepDefinitions() {
    When("^the slot is booked by user (.*)$", (Long userId) ->
      storeException(() -> storeBooking(() -> bookSlot(userId))));
    Then("^the slot should be booked by the user (.*)$", (Long userId) -> {
      BookingView booking = bookingComponent.find(userId, bookingHolder.bookingCreated.getId());
      assertThat(booking.getSlotId()).isEqualTo(bookingHolder.bookingCreated.getSlotId());
      assertThat(booking.getRoomId()).isEqualTo(bookingHolder.bookingCreated.getRoomId());
      assertThat(booking.getUserId()).isEqualTo(bookingHolder.bookingCreated.getUserId());
      assertThat(booking.getBookingTime()).isEqualTo(bookingHolder.bookingCreated.getBookingTime());
    });
    Then("^a notification of a created booking should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(BookingCreatedEvent.class);
      BookingCreatedEvent bookingCreatedEvent = (BookingCreatedEvent) messageCaptor.getValue();
      assertThat(bookingCreatedEvent.getUserId()).isEqualTo(bookingHolder.bookingCreated.getUserId());
      assertThat(bookingCreatedEvent.getBookingId()).isEqualTo(bookingHolder.bookingCreated.getId());
      assertThat(bookingCreatedEvent.getSlotId()).isEqualTo(bookingHolder.bookingCreated.getSlotId());
    });
    Then("^the user should get a notification that the slot is already booked$", () ->
      assertThat(exceptionHolder.exceptionThrown).isInstanceOf(SlotAlreadyBookedException.class));
    When("^the user (.*) asks for his bookings$", (Long userId) ->
      bookingHolder.bookingsFetched = bookingComponent.getFor(userId));
    Then("^the user should see all slots booked by him$", () ->
      assertThat(bookingHolder.bookingsFetched).hasSameElementsAs(bookingHolder.bookingsCreated));
    Given("^the slot start time is passed$", () ->
      clock.setClock(Clock.fixed(Instant.now().plus(20, ChronoUnit.DAYS), ZoneId.systemDefault())));
    Then("^the user should be notified the booking does belong to other user$", () ->
      assertThat(exceptionHolder.exceptionThrown).isNotNull().isInstanceOf(WrongBookingUserException.class));
    Then("^the user should be notified the slot is still in auction$", () ->
      assertThat(exceptionHolder.exceptionThrown).isInstanceOf(SlotPendingAuctionException.class));
    Then("^the slot shouldn't be booked by the user (.*)$", (Integer userId) ->
      assertThat(bookingComponent.findAllBy(userId)).isEmpty());
  }

  private BookingView bookSlot(Long userId) {
    return createBookingUseCase.book(new CreateBookingCommand(userId, roomHolder.roomCreated.getId(), slotHolder.slotCreated.getId()));
  }

  private void storeBooking(Supplier<BookingView> supplier) {
    bookingHolder.bookingCreated = supplier.get();
    bookingHolder.bookingsCreated.add(bookingHolder.bookingCreated);
  }
}

