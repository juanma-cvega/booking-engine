package com.jusoft.component.booking;

import java.util.List;
import java.util.Optional;

interface BookingRepository {

    void save(Booking newBooking);

    void delete(long bookingId);

    Optional<Booking> find(long bookingId);

    List<Booking> getByUser(long userId);
}
