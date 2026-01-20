package com.jusoft.bookingengine.usecase.booking;

import static com.jusoft.bookingengine.holder.DataHolder.bookingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.bookingsCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateBookingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private BookingManagerComponent bookingManagerComponent;

    @Autowired private SlotManagerComponent slotManagerComponent;

    @Autowired private CreateBookingUseCase createBookingUseCase;

    @When("^there is a booking created by user (.*)$")
    public void there_is_a_booking_created_by_user(Long userId) {
        storeBooking(() -> bookSlot(userId));
    }

    @Then("^the slot should be booked by the user (.*)$")
    public void the_slot_should_be_booked_by_the_user(Long userId) {
        BookingView booking = bookingManagerComponent.find(bookingCreated.id());
        assertThat(booking.slotId()).isEqualTo(bookingCreated.slotId());
        assertThat(booking.userId()).isEqualTo(bookingCreated.userId());
        assertThat(booking.bookingTime()).isEqualTo(bookingCreated.bookingTime());
    }

    @Then("^a notification of a created booking should be published$")
    public void a_notification_of_a_created_booking_should_be_published() {
        verify(messagePublisher).publish(messageCaptor.capture());
        assertThat(messageCaptor.getValue()).isInstanceOf(BookingCreatedEvent.class);
        BookingCreatedEvent bookingCreatedEvent = (BookingCreatedEvent) messageCaptor.getValue();
        assertThat(bookingCreatedEvent.userId()).isEqualTo(bookingCreated.userId());
        assertThat(bookingCreatedEvent.bookingId()).isEqualTo(bookingCreated.id());
        assertThat(bookingCreatedEvent.slotId()).isEqualTo(bookingCreated.slotId());
    }

    private BookingView bookSlot(Long userId) {
        return createBookingUseCase.book(new CreateBookingCommand(userId, slotCreated.getId()));
    }

    private void storeBooking(Supplier<BookingView> supplier) {
        bookingCreated = supplier.get();
        bookingsCreated.add(bookingCreated);
    }
}
