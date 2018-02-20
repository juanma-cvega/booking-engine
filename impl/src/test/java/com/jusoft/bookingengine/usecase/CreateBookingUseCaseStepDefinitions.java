package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

import static com.jusoft.bookingengine.holder.DataHolder.bookingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.bookingsCreated;
import static com.jusoft.bookingengine.holder.DataHolder.bookingsFetched;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private BookingManagerComponent bookingManagerComponent;
  @Autowired
  private SlotManagerComponent slotManagerComponent;

  @Autowired
  private CreateBookingUseCase createBookingUseCase;

  public CreateBookingUseCaseStepDefinitions() {
    When("^there is a booking created by user (.*)$", (Long userId) ->
      storeBooking(() -> bookSlot(userId)));
    Then("^the slot should be booked by the user (.*)$", (Long userId) -> {
      BookingView booking = bookingManagerComponent.find(userId, bookingCreated.getId());
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
    When("^the user (.*) asks for his bookings$", (Long userId) ->
      bookingsFetched = bookingManagerComponent.getFor(userId));
    Then("^the user should see all slots booked by him$", () ->
      assertThat(bookingsFetched).hasSameElementsAs(bookingsCreated));
  }

  private BookingView bookSlot(Long userId) {
    return createBookingUseCase.book(CreateBookingCommand.of(userId, slotCreated.getId()));
  }

  private void storeBooking(Supplier<BookingView> supplier) {
    bookingCreated = supplier.get();
    bookingsCreated.add(bookingCreated);
  }
}

