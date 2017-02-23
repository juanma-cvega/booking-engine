package com.jusoft.component.booking;

public interface BookingComponent {

    BookingResource book(CreateBookingRequest createBookingCommand);

    void cancel(Long userId, Long bookingId);

    BookingResource find(Long userId, Long bookingId);

    BookingResources getFor(Long userId);
}
