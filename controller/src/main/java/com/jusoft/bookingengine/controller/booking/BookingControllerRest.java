package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.booking.api.CreateBookingRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bookings")
class BookingControllerRest {

    private final BookingManagerComponent bookingManagerComponent;
    private final BookingCommandFactory bookingCommandFactory;
    private final BookingResourceFactory bookingResourceFactory;

    BookingControllerRest(
            BookingManagerComponent bookingManagerComponent,
            BookingCommandFactory bookingCommandFactory,
            BookingResourceFactory bookingResourceFactory) {
        this.bookingManagerComponent = bookingManagerComponent;
        this.bookingCommandFactory = bookingCommandFactory;
        this.bookingResourceFactory = bookingResourceFactory;
    }

    @PostMapping(
            value = "/room/{roomId}/slot/{slotId}/booking",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResource book(
            @PathVariable long roomId,
            @PathVariable long slotId,
            @Valid @RequestBody CreateBookingRequest command) {
        log.info(
                "Create booking request received: roomId={}, slotId={}, userId={}",
                roomId,
                slotId,
                command.getUserId());
        BookingView booking =
                bookingManagerComponent.book(
                        bookingCommandFactory.createFrom(slotId, command.getUserId()));
        BookingResource bookingResource = bookingResourceFactory.createFrom(booking);
        log.info("Create booking request finished: booking={}", bookingResource);
        return bookingResource;
    }

    @DeleteMapping(value = "/user/{userId}/booking/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable long userId, @PathVariable long bookingId) {
        log.info("Cancel booking request received: userId={}, bookingId={}", userId, bookingId);
        bookingManagerComponent.cancel(userId, bookingId);
        log.info("Cancel booking request finished");
    }

    @GetMapping(value = "/user/{userId}/booking/{bookingId}", produces = "application/json")
    public BookingResource find(@PathVariable long userId, @PathVariable long bookingId) {
        log.info("Find booking request received: userId={}, bookingId={}", userId, bookingId);
        BookingView booking = bookingManagerComponent.find(bookingId);
        BookingResource bookingResource = bookingResourceFactory.createFrom(booking);
        log.info("Find booking request finished: booking={}", bookingResource);
        return bookingResource;
    }

    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public BookingResources getFor(@PathVariable long userId) {
        log.info("Create booking request received: userId={}", userId);
        List<BookingView> bookings = bookingManagerComponent.findAllBy(userId);
        BookingResources bookingResources = bookingResourceFactory.createFrom(bookings);
        log.info(
                "Create booking request finished: userId={}, bookings={}",
                userId,
                bookingResources.getBookings().size());
        return bookingResources;
    }
}
