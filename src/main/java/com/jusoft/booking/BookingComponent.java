package com.jusoft.booking;

import java.util.List;

public interface BookingComponent {

    BookingResource book(Long userId, Long roomId, Long slotId);

    void cancel(Long userId, Long bookingId);

    BookingResource find(Long userId, Long bookingId);

    List<BookingResource> getFor(Long userId);
}
