package com.jusoft.booking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@RequestMapping(value = "/bookings")
public class BookingComponentRest implements BookingComponent {
    private final BookingService bookingService;
    private final BookingResourceFactory bookingResourceFactory;

    BookingComponentRest(BookingService bookingService, BookingResourceFactory bookingResourceFactory) {
        this.bookingService = bookingService;
        this.bookingResourceFactory = bookingResourceFactory;
    }

    @Override
    @RequestMapping(consumes = "application/json", produces = "application/json", method = RequestMethod.POST, value = "/user/{userId}/room/{roomId}/slot/{slotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@PathVariable Long userId, @PathVariable Long roomId, @PathVariable Long slotId) {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest(userId, roomId, slotId, now());
        bookingService.add(createBookingRequest);
    }

    @Override
    @RequestMapping(consumes = "application/json", produces = "application/json", method = RequestMethod.DELETE, value = "/user/{userId}/slot/{slotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long userId, @PathVariable Long slotId) {
        CancelBookingRequest cancelBookingRequest = new CancelBookingRequest(userId, slotId, now());
        bookingService.cancel(cancelBookingRequest);
    }

    @Override
    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/user/{userId}")
    public List<BookingResource> getBookingsFor(@PathVariable Long userId) {
        return bookingService.getBookingsFor(userId).stream().map(bookingResourceFactory::createFrom).collect(toList());
    }
}
