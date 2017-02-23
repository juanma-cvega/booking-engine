package com.jusoft.component;

import com.jusoft.component.booking.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping(value = "/bookings", consumes = "application/json", produces = "application/json")
class BookingComponentRest implements BookingComponent {
    private final BookingComponent bookingComponent;

    BookingComponentRest(BookingComponent bookingComponent) {
        this.bookingComponent = bookingComponent;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookingResource book(@RequestBody CreateBookingRequest createBookingRequest) {
        log.info("Create booking request received: createBookingRequest={}", createBookingRequest);
        BookingResource booking = bookingComponent.book(createBookingRequest);
        log.info("Create booking request finished: booking={}", booking);
        return booking;
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}/booking/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long userId, @PathVariable Long bookingId) {
        log.info("Cancel booking request received: userId={}, bookingId={}", userId, bookingId);
        bookingComponent.cancel(userId, bookingId);
        log.info("Cancel booking request finished");
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/booking/{bookingId}")
    @ResponseBody
    public BookingResource find(@PathVariable Long userId, @PathVariable Long bookingId) {
        log.info("Find booking request received: userId={}, bookingId={}", userId, bookingId);
        BookingResource booking = bookingComponent.find(userId, bookingId);
        log.info("Find booking request finished: booking={}", booking);
        return booking;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @ResponseBody
    public BookingResources getFor(@PathVariable Long userId) {
        log.info("Create booking request received: userId={}", userId);
        BookingResources bookings = bookingComponent.getFor(userId);
        log.info("Create booking request finished: userId={}, bookings={}", userId, bookings.getBookings().size());
        return bookings;
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
