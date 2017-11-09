package com.jusoft.component.booking;

import com.jusoft.component.AbstractStepDefinitions;
import com.jusoft.component.booking.api.BookingComponent;
import com.jusoft.component.booking.api.BookingNotFoundException;
import com.jusoft.component.booking.api.CancelBookingCommand;
import com.jusoft.component.booking.api.CreateBookingCommand;
import com.jusoft.component.booking.api.SlotAlreadyBookedException;
import com.jusoft.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.component.booking.api.WrongBookingUserException;
import com.jusoft.component.mock.ClockStub;
import com.jusoft.component.room.RoomHolder;
import com.jusoft.component.slot.SlotHolder;
import com.jusoft.component.slot.api.SlotComponent;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BookingManagementStepDefinitions extends AbstractStepDefinitions {

  @Autowired
  private BookingComponent bookingComponent;

  @Autowired
  private SlotComponent slotComponent;

  @Autowired
  private BookingHolder bookingHolder;

  @Autowired
  private SlotHolder slotHolder;

  @Autowired
  private RoomHolder roomHolder;

  @Autowired
  private ClockStub clock;

  private RuntimeException exceptionThrown;

  public BookingManagementStepDefinitions() {
    Given("^a slot from the room is selected", () -> slotHolder.slotSelected = getSlotFrom(roomHolder.roomCreated.getId()));
    When("^the slot is booked$", () ->
      storeBooking(() -> bookSlot(USER_ID_1, slotHolder.slotSelected)));
    Then("^the slot should be booked by the user$", () -> {
      Booking booking = bookingComponent.find(USER_ID_1, bookingHolder.bookingCreated.getId());
      assertThat(booking).isNotNull();
      Assertions.assertThat(booking.getSlot().getId()).isEqualTo(slotHolder.slotSelected);
    });
    Given("^all slots from the room are booked by the same user$", () ->
      slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()).forEach(slotResource ->
        storeBooking(() -> bookSlot(USER_ID_1, slotResource.getId()))));
    When("^the user asks for his bookings$", () ->
      bookingHolder.bookingsFetched = bookingComponent.getFor(USER_ID_1));
    Then("^the user should see all slots booked by him$", () ->
      assertThat(bookingHolder.bookingsFetched).hasSameElementsAs(bookingHolder.bookingsCreated));
    When("^a new user books the slot$", () ->
      storeException(() -> bookingHolder.bookingCreated = bookSlot(USER_ID_2, slotHolder.slotSelected)));
    Then("^the user should get a notification that the slot is already booked$", () ->
      assertThat(exceptionThrown).isInstanceOf(SlotAlreadyBookedException.class));
    When("^the user cancels the booking$", () ->
      storeException(() -> bookingComponent.cancel(new CancelBookingCommand(USER_ID_1, bookingHolder.bookingCreated.getId()))));
    Then("^the user should not see that booking in his list$", () ->
      assertThatExceptionOfType(BookingNotFoundException.class)
        .isThrownBy(() -> bookingComponent.find(USER_ID_1, bookingHolder.bookingCreated.getId())));
    Given("^the slot start time is passed$", () -> clock.setClock(Clock.fixed(Instant.now().plus(20, ChronoUnit.DAYS), ZoneId.systemDefault())));
    Then("^the user should be notified the booking is already started$", () ->
      assertThat(exceptionThrown)
        .isNotNull()
        .isInstanceOf(SlotAlreadyStartedException.class));
    When("^a different user cancels the booking$", () ->
      storeException(() -> bookingComponent.cancel(new CancelBookingCommand(USER_ID_2, bookingHolder.bookingCreated.getId()))));
    And("^the user should be notified the booking does belong to other user$", () ->
      assertThat(exceptionThrown).isNotNull().isInstanceOf(WrongBookingUserException.class));
  }

  private Long getSlotFrom(long roomId) {
    return slotComponent.findLastCreatedFor(roomId)
      .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to find last slot for room %s", roomId)))
      .getId();
  }

  private Booking bookSlot(Long userId, long slotId) {
    return bookingComponent.book(new CreateBookingCommand(userId, ROOM_ID, slotId));
  }

  private void storeBooking(Supplier<Booking> supplier) {
    bookingHolder.bookingCreated = supplier.get();
    bookingHolder.bookingsCreated.add(bookingHolder.bookingCreated);
  }

  private void storeException(Runnable runnable) {
    try {
      runnable.run();
    } catch (RuntimeException exception) {
      exceptionThrown = exception;
    }
  }
}
