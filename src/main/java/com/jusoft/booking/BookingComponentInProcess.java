package com.jusoft.booking;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

public class BookingComponentInProcess implements BookingComponent {

    private final BookingService bookingService;
    private final BookingResourceFactory bookingResourceFactory;

    BookingComponentInProcess(BookingService bookingService, BookingResourceFactory bookingResourceFactory) {
        this.bookingService = bookingService;
        this.bookingResourceFactory = bookingResourceFactory;
    }

    @Override
    public BookingResource book(Long userId, Long roomId, Long slotId) {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest(userId, roomId, slotId, now());
        Booking newBooking = bookingService.book(createBookingRequest);
        return bookingResourceFactory.createFrom(newBooking);
    }

    @Override
    public void cancel(Long userId, Long bookingId) {
        CancelBookingRequest cancelBookingRequest = new CancelBookingRequest(userId, bookingId, now());
        bookingService.cancel(cancelBookingRequest);
    }

    @Override
    public BookingResource find(Long userId, Long bookingId) {
        return bookingResourceFactory.createFrom(bookingService.find(userId, bookingId));
    }

    @Override
    public List<BookingResource> getFor(Long userId) {
        return bookingService.getFor(userId).stream().map(bookingResourceFactory::createFrom).collect(toList());
    }
}
