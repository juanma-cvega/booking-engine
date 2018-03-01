//package com.jusoft.bookingengine.component.booking;
//
//import com.jusoft.bookingengine.component.AbstractStepDefinitions;
//import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
//import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
//import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
//import com.jusoft.bookingengine.component.booking.api.BookingView;
//import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
//import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
//import com.jusoft.bookingengine.component.booking.api.SlotAlreadyReservedException;
//import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
//import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
//import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
//import com.jusoft.bookingengine.fixture.CommonFixtures;
//import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
//import com.jusoft.bookingengine.holder.BookingHolder;
//import com.jusoft.bookingengine.holder.RoomHolder;
//import com.jusoft.bookingengine.holder.SlotHolder;
//import com.jusoft.bookingengine.usecase.auction.AddBidderToAuctionUseCase;
//import com.jusoft.bookingengine.usecase.booking.CancelBookingUseCase;
//import com.jusoft.bookingengine.usecase.booking.CreateBookingUseCase;
//import org.assertj.core.api.Assertions;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.Clock;
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.function.Supplier;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//
//public class BookingManagementStepDefinitions extends AbstractStepDefinitions {
//
//  @Autowired
//  private CancelBookingUseCase cancelBookingUseCase;
//
//  @Autowired
//  private CreateBookingUseCase createBookingUseCase;
//
//  @Autowired
//  private AddBidderToAuctionUseCase addBidderToAuctionUseCase;
//
//  @Autowired
//  private BookingManagerComponent bookingComponent;
//
//  @Autowired
//  private SlotManagerComponent slotComponent;
//
//  @Autowired
//  private BookingHolder bookingHolder;
//
//  @Autowired
//  private SlotHolder slotHolder;
//
//  @Autowired
//  private RoomHolder roomHolder;
//
//  private RuntimeException exceptionThrown;
//
//  public BookingManagementStepDefinitions() {
//    Given("^a slot from the room is selected", () ->
//      slotHolder.slotSelected = getSlotFrom(roomHolder.roomCreated.getId()));
//    When("^there is a booking created by user (.*)$", (Long userId) ->
//
//      storeException(() -> storeBooking(() -> bookSlot(userId, slotHolder.slotSelected))));
//    Then("^the slot should be booked by the user (.*)$", (Long userId) -> {
//      BookingView booking = bookingComponent.find(userId, extractBookingId(userId));
//      assertThat(booking).isNotNull();
//      Assertions.assertThat(booking.getSlotId()).isEqualTo(slotHolder.slotSelected);
//    });
//    Given("^all slots from the room are booked by user (.*)$", (Long userId) ->
//      slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()).forEach(slotResource ->
//        storeBooking(() -> bookSlot(userId, slotResource.getId()))));
//    When("^the user (.*) asks for his bookings$", (Long userId) ->
//      bookingHolder.bookingsFetched = bookingComponent.getFor(userId));
//    Then("^the user should see all slots booked by him$", () ->
//      assertThat(bookingHolder.bookingsFetched).hasSameElementsAs(bookingHolder.bookingsCreated));
//    Then("^the user should get a notification that the slot is already booked$", () ->
//      assertThat(exceptionThrown).isInstanceOf(SlotAlreadyReservedException.class));
//    When("^the user (.*) cancels the booking from user (.*)$", (Long userToCancel, Long userOwner) ->
//      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userToCancel, extractBookingId(userOwner)))));
//    When("^the user (.*) cancels his booking$", (Long userId) ->
//      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userId, extractBookingId(userId)))));
//    Then("^the user (.*) should not see that booking in his list$", (Long userId) ->
//      assertThatExceptionOfType(BookingNotFoundException.class)
//        .isThrownBy(() -> bookingComponent.find(userId, extractBookingId(userId))));
//    Given("^the slot start time is passed$", () ->
//      clock.setClock(Clock.fixed(Instant.now().plus(20, ChronoUnit.DAYS), ZoneId.systemDefault())));
//    Given("^the slot is booked during auction time by user (.*)$", (Long userId) ->
//      storeBooking(() -> bookSlot(userId, slotHolder.slotSelected)));
//    Then("^the user should be notified the booking is already started$", () ->
//      assertThat(exceptionThrown)
//        .isNotNull()
//        .isInstanceOf(SlotNotAvailableException.class));
//    When("^user (.*) cancels the booking$", (Long userId) ->
//      storeException(() -> cancelBookingUseCase.cancel(new CancelBookingCommand(userId, extractBookingId(userId)))));
//    When("^the user (.*) enters the auction$", (Integer userId) ->
//      addBidderToAuctionUseCase.addBidderTo(slotHolder.slotSelected, userId));
//    When("^the auction time is finished at (.*)$", (String currentTime) -> {
//      ZonedDateTime previous = ZonedDateTime.now(clock);
//      clock.setClock(getFixedClockAt(currentTime));
//      scheduledTasksExecutor.executeLateTasks(ZonedDateTime.now(clock), previous);
//    });
//    Then("^the user should be notified the booking does belong to other user$", () ->
//      assertThat(exceptionThrown).isNotNull().isInstanceOf(WrongBookingUserException.class));
//    Then("^the user should be notified the slot does not exist still in auction$", () ->
//      assertThat(exceptionThrown).isInstanceOf(SlotPendingAuctionException.class));
//    Then("^the slot shouldn't be booked by the user (.*)$", (Integer userId) ->
//      assertThat(bookingComponent.findAllBy(userId)).isEmpty());
//  }
//
//  private long extractBookingId(long userId) {
//    return messagesSink.getMessages(BookingCreatedEvent.class)
//      .orElseThrow(() -> new IllegalArgumentException(String.format("Empty list found for message of type %s", BookingCreatedEvent.class))).stream()
//      .filter(bookingCreatedEvent -> bookingCreatedEvent.getUserId() == userId)
//      .findFirst()
//      .orElseThrow(() -> new IllegalArgumentException(String.format("No booking messages found of type %s for userId %s", BookingCreatedEvent.class, userId)))
//      .getBookingId();
//  }
//
//  private Long getSlotFrom(long roomId) {
//    return slotComponent.findLastCreatedFor(roomId)
//      .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to findBySlot last slot for room %s", roomId)))
//      .getId();
//  }
//
//  private BookingView bookSlot(Long userId, long slotId) {
//    return createBookingUseCase.reserve(new CreateBookingCommand(userId, CommonFixtures.ROOM_ID, slotId));
//  }
//
//  private void storeBooking(Supplier<BookingView> supplier) {
//    bookingHolder.bookingCreated = supplier.get();
//    bookingHolder.bookingsCreated.add(bookingHolder.bookingCreated);
//  }
//
//  private void storeException(Runnable runnable) {
//    try {
//      runnable.run();
//    } catch (RuntimeException exception) {
//      exceptionThrown = exception;
//    }
//  }
//}
