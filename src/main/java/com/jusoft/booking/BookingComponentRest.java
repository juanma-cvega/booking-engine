package com.jusoft.booking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping(value = "/bookings", consumes = "application/json", produces = "application/json")
public class BookingComponentRest implements BookingComponent {
    private final BookingComponent bookingComponent;

    BookingComponentRest(BookingComponent bookingComponent) {
        this.bookingComponent = bookingComponent;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/room/{roomId}/slot/{slotId}")
    public BookingResource book(@PathVariable Long userId, @PathVariable Long roomId, @PathVariable Long slotId) {
        return bookingComponent.book(userId, roomId, slotId);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}/booking/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long userId, @PathVariable Long bookingId) {
        bookingComponent.cancel(userId, bookingId);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/booking/{bookingId}")
    public BookingResource find(@PathVariable Long userId, @PathVariable Long bookingId) {
        return bookingComponent.find(userId, bookingId);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public List<BookingResource> getFor(@PathVariable Long userId) {
        return bookingComponent.getFor(userId);
    }
}
