package com.jusoft.component.booking;

import java.util.List;

public interface BookingComponent {

    Booking book(CreateBookingCommand createBookingCommand);

    void cancel(CancelBookingCommand cancelBookingCommand);

    Booking find(long userId, long bookingId);

    List<Booking> getFor(long userId);
}
