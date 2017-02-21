package com.jusoft.booking;

import com.jusoft.AbstractStepDefinitions;
import com.jusoft.holder.BookingHolder;
import com.jusoft.holder.SlotHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

import static com.jusoft.fixtures.CommonFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BookingManagementStepDefinitions extends AbstractStepDefinitions {

    @Autowired
    private BookingComponent bookingComponentInProcess;

    @Autowired
    private SlotHolder slotHolder;

    @Autowired
    private BookingHolder bookingHolder;

    private RuntimeException exceptionThrown;

    public BookingManagementStepDefinitions() {
        When("^the slot is booked$", () -> storeBooking(() -> bookSlot(USER_ID_1, slotHolder.slotCreated.getSlotId())));
        Then("^the slot should be booked by the user$", () -> {
            BookingResource bookingResource = bookingComponentInProcess.find(USER_ID_1, slotHolder.slotCreated.getSlotId());
            assertThat(bookingResource).isNotNull();
            assertThat(bookingResource.getSlot().getSlotId()).isEqualTo(slotHolder.slotCreated.getSlotId());
        });
        Given("^all slots are booked by the same user$", () -> slotHolder.slotsCreated.forEach(slotResource ->
                storeBooking(() -> bookSlot(USER_ID_1, slotResource.getSlotId()))));
        When("^the user asks for his bookings$", () -> bookingHolder.bookingsFetched = bookingComponentInProcess.getFor(USER_ID_1));
        Then("^the user should see all slots booked by him$", () -> assertThat(bookingHolder.bookingsFetched).hasSameElementsAs(bookingHolder.bookingsCreated));
        When("^a new user books the slot$", () -> {
            try {
                bookingHolder.bookingCreated = bookSlot(USER_ID_2, slotHolder.slotCreated.getSlotId());
            } catch (RuntimeException exception) {
                exceptionThrown = exception;
            }
        });
        Then("^the user should get a notification that the slot is already booked$", () -> assertThat(exceptionThrown).isInstanceOf(SlotAlreadyBookedException.class));
        When("^the user cancels the booking$", () -> bookingComponentInProcess.cancel(USER_ID_1, bookingHolder.bookingCreated.getBookingId()));
        Then("^the user should not see that booking in his list$", () -> assertThatExceptionOfType(BookingNotFoundException.class)
                .isThrownBy(() -> bookingComponentInProcess.find(USER_ID_1, bookingHolder.bookingCreated.getBookingId())));
    }

    private BookingResource bookSlot(Long userId, long slotId) {
        return bookingComponentInProcess.book(userId, ROOM_ID, slotId);
    }

    private void storeBooking(Supplier<BookingResource> supplier) {
        bookingHolder.bookingCreated = supplier.get();
        bookingHolder.bookingsCreated.add(bookingHolder.bookingCreated);
    }
}
