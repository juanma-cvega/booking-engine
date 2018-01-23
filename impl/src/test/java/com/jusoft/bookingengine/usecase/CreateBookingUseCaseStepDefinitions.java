package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyBookedException;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import static com.jusoft.bookingengine.holder.DataHolder.bookingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.bookingsCreated;
import static com.jusoft.bookingengine.holder.DataHolder.bookingsFetched;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
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
      BookingView booking = bookingComponent.find(userId, bookingCreated.getId());
      assertThat(booking.getSlotId()).isEqualTo(bookingCreated.getSlotId());
      assertThat(booking.getUserId()).isEqualTo(bookingCreated.getUserId());
      assertThat(booking.getBookingTime()).isEqualTo(bookingCreated.getBookingTime());
    });
    Then("^a notification of a created booking should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(BookingCreatedEvent.class);
      BookingCreatedEvent bookingCreatedEvent = (BookingCreatedEvent) messageCaptor.getValue();
      assertThat(bookingCreatedEvent.getUserId()).isEqualTo(bookingCreated.getUserId());
      assertThat(bookingCreatedEvent.getBookingId()).isEqualTo(bookingCreated.getId());
      assertThat(bookingCreatedEvent.getSlotId()).isEqualTo(bookingCreated.getSlotId());
    });
    Then("^the user should get a notification that the slot is already booked$", () -> {
      assertThat(exceptionThrown).isInstanceOf(SlotAlreadyBookedException.class);
      SlotAlreadyBookedException exception = (SlotAlreadyBookedException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
    When("^the user (.*) asks for his bookings$", (Long userId) ->
      bookingsFetched = bookingComponent.getFor(userId));
    Then("^the user should see all slots booked by him$", () ->
      assertThat(bookingsFetched).hasSameElementsAs(bookingsCreated));
    Given("^the slot start time is passed$", () ->
      clock.setClock(Clock.fixed(Instant.now().plus(20, ChronoUnit.DAYS), ZoneId.systemDefault())));
    Then("^the user should be notified the slot is still in auction$", () -> {
      assertThat(exceptionThrown).isInstanceOf(SlotPendingAuctionException.class);
      SlotPendingAuctionException exception = (SlotPendingAuctionException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
    Then("^the slot shouldn't be booked by the user (.*)$", (Integer userId) ->
      assertThat(bookingComponent.findAllBy(userId)).isEmpty());
    Then("^the user should get a notification that the slot is already started$", () -> {
      assertThat(exceptionThrown).isInstanceOf(SlotAlreadyStartedException.class);
      SlotAlreadyStartedException exception = (SlotAlreadyStartedException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
    Then("^the user (.*) should get a notification that he is not a member of the club$", (Long userId) -> {
      assertThat(exceptionThrown).isInstanceOf(UserNotMemberException.class);
      UserNotMemberException exception = (UserNotMemberException) exceptionThrown;
      assertThat(exception.getUserId()).isEqualTo(userId);
      assertThat(exception.getClubId()).isEqualTo(clubCreated.getId());
    });
  }

  private BookingView bookSlot(Long userId) {
    return createBookingUseCase.book(new CreateBookingCommand(userId, slotCreated.getId()));
  }

  private void storeBooking(Supplier<BookingView> supplier) {
    bookingCreated = supplier.get();
    bookingsCreated.add(bookingCreated);
  }
}

