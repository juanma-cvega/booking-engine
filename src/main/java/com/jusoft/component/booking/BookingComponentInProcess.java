package com.jusoft.component.booking;

class BookingComponentInProcess implements BookingComponent {

    private final BookingService bookingService;
    private final BookingResourceFactory bookingResourceFactory;
    private final BookingRequestFactory bookingRequestFactory;

    BookingComponentInProcess(BookingService bookingService, BookingResourceFactory bookingResourceFactory, BookingRequestFactory bookingRequestFactory) {
        this.bookingService = bookingService;
        this.bookingResourceFactory = bookingResourceFactory;
        this.bookingRequestFactory = bookingRequestFactory;
    }

    @Override
    public BookingResource book(CreateBookingRequest createBookingRequest) {
        Booking newBooking = bookingService.book(bookingRequestFactory.createFrom(createBookingRequest));
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
    public BookingResources getFor(Long userId) {
        return bookingResourceFactory.createFrom(bookingService.getFor(userId));
    }
}
