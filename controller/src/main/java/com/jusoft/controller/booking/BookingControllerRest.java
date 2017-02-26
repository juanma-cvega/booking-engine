package com.jusoft.controller.booking;

import com.jusoft.component.booking.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping(value = "/bookings")
class BookingControllerRest {

    private final BookingComponent bookingComponent;
    private final BookingCommandFactory bookingCommandFactory;
    private final BookingResourceFactory bookingResourceFactory;

    BookingControllerRest(BookingComponent bookingComponent, BookingCommandFactory bookingCommandFactory, BookingResourceFactory bookingResourceFactory) {
        this.bookingComponent = bookingComponent;
        this.bookingCommandFactory = bookingCommandFactory;
        this.bookingResourceFactory = bookingResourceFactory;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookingResource book(@Valid @RequestBody CreateBookingRequest createBookingRequest) {
        log.info("Create booking request received: createBookingRequest={}", createBookingRequest);
        Booking booking = bookingComponent.book(bookingCommandFactory.createFrom(createBookingRequest));
        BookingResource bookingResource = bookingResourceFactory.createFrom(booking);
        log.info("Create booking request finished: booking={}", bookingResource);
        return bookingResource;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}/booking/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long userId, @PathVariable Long bookingId) {
        log.info("Cancel booking request received: userId={}, bookingId={}", userId, bookingId);
        bookingComponent.cancel(bookingCommandFactory.createFrom(userId, bookingId));
        log.info("Cancel booking request finished");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/booking/{bookingId}", produces = "application/json")
    @ResponseBody
    public BookingResource find(@PathVariable Long userId, @PathVariable Long bookingId) {
        log.info("Find booking request received: userId={}, bookingId={}", userId, bookingId);
        Booking booking = bookingComponent.find(userId, bookingId);
        BookingResource bookingResource = bookingResourceFactory.createFrom(booking);
        log.info("Find booking request finished: booking={}", bookingResource);
        return bookingResource;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}", produces = "application/json")
    @ResponseBody
    public BookingResources getFor(@PathVariable Long userId) {
        log.info("Create booking request received: userId={}", userId);
        List<Booking> bookings = bookingComponent.getFor(userId);
        BookingResources bookingResources = bookingResourceFactory.createFrom(bookings);
        log.info("Create booking request finished: userId={}, bookings={}", userId, bookingResources.getBookings().size());
        return bookingResources;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Booking not found")
    @ExceptionHandler(BookingNotFoundException.class)
    public void bookingNotFoundException() {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Slot already booked")
    @ExceptionHandler(SlotAlreadyBookedException.class)
    public void slotAlreadyBookedException() {
    }

    @ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = "Slot already started")
    @ExceptionHandler(SlotAlreadyStartedException.class)
    public void slotAlreadyStartedException() {
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Booking does not belong to user")
    @ExceptionHandler(WrongBookingUserException.class)
    public void wrongBookingUserException() {
    }
}
