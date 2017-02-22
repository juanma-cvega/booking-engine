package com.jusoft.booking;

import com.jusoft.AbstractStepDefinitions;
import com.jusoft.mock.InstantSupplierStub;
import com.jusoft.slot.SlotHolder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import static com.jusoft.fixtures.CommonFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BookingManagementStepDefinitions extends AbstractStepDefinitions {

    @Resource(name = "bookingComponentInProcess")
    @Autowired
    private BookingComponent bookingComponent;

    @Autowired
    private SlotHolder slotHolder;

    @Autowired
    private BookingHolder bookingHolder;

    @Autowired
    private InstantSupplierStub instantSupplier;

    private RuntimeException exceptionThrown;

    public BookingManagementStepDefinitions() {
        When("^the slot is booked$", () -> storeBooking(() -> bookSlot(USER_ID_1, slotHolder.slotCreated.getSlotId())));
        Then("^the slot should be booked by the user$", () -> {
            BookingResource bookingResource = bookingComponent.find(USER_ID_1, slotHolder.slotCreated.getSlotId());
            assertThat(bookingResource).isNotNull();
            assertThat(bookingResource.getSlot().getSlotId()).isEqualTo(slotHolder.slotCreated.getSlotId());
        });
        Given("^all slots are booked by the same user$", () -> slotHolder.slotsCreated.forEach(slotResource ->
                storeBooking(() -> bookSlot(USER_ID_1, slotResource.getSlotId()))));
        When("^the user asks for his bookings$", () -> bookingHolder.bookingsFetched = bookingComponent.getFor(USER_ID_1));
        Then("^the user should see all slots booked by him$", () -> assertThat(bookingHolder.bookingsFetched).hasSameElementsAs(bookingHolder.bookingsCreated));
        When("^a new user books the slot$", () -> storeException(() -> bookingHolder.bookingCreated = bookSlot(USER_ID_2, slotHolder.slotCreated.getSlotId())));
        Then("^the user should get a notification that the slot is already booked$", () -> assertThat(exceptionThrown).isInstanceOf(SlotAlreadyBookedException.class));
        When("^the user cancels the booking$", () -> storeException(() -> bookingComponent.cancel(USER_ID_1, bookingHolder.bookingCreated.getBookingId())));
        Then("^the user should not see that booking in his list$", () -> assertThatExceptionOfType(BookingNotFoundException.class)
                .isThrownBy(() -> bookingComponent.find(USER_ID_1, bookingHolder.bookingCreated.getBookingId())));
        Given("^the slot start time is passed$", () -> instantSupplier.setClock(Clock.fixed(Instant.now().plus(2, ChronoUnit.DAYS), ZoneId.systemDefault())));
        Then("^the user should be notified the booking is already started$", () -> assertThat(exceptionThrown)
                .isNotNull()
                .isInstanceOf(SlotAlreadyStartedException.class));
        When("^a different user cancels the booking$", () -> storeException(() -> bookingComponent.cancel(USER_ID_2, bookingHolder.bookingCreated.getBookingId())));
        And("^the user should be notified the booking does belong to other user$", () -> assertThat(exceptionThrown).isNotNull().isInstanceOf(WrongUserForSlotException.class));
    }

    private BookingResource bookSlot(Long userId, long slotId) {
        return bookingComponent.book(userId, ROOM_ID, slotId);
    }

    private void storeBooking(Supplier<BookingResource> supplier) {
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
