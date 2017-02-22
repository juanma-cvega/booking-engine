package com.jusoft.booking;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BookingComponentInProcess implements BookingComponent {

    private final BookingService bookingService;
    private final BookingResourceFactory bookingResourceFactory;
    private final BookingRequestFactory bookingRequestFactory;

    BookingComponentInProcess(BookingService bookingService, BookingResourceFactory bookingResourceFactory, BookingRequestFactory bookingRequestFactory) {
        this.bookingService = bookingService;
        this.bookingResourceFactory = bookingResourceFactory;
        this.bookingRequestFactory = bookingRequestFactory;
    }

    @Override
    public BookingResource book(Long userId, Long roomId, Long slotId) {
        Booking newBooking = bookingService.book(bookingRequestFactory.createFrom(userId, roomId, slotId));
        return bookingResourceFactory.createFrom(newBooking);
    }

    @Override
    public void cancel(Long userId, Long bookingId) {
        bookingService.cancel(bookingRequestFactory.createFrom(userId, bookingId));
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
