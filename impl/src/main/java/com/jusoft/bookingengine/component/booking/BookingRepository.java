package com.jusoft.bookingengine.component.booking;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

interface BookingRepository {

  void save(Booking newBooking);

  boolean delete(long bookingId);

  Optional<Booking> find(long bookingId);

  List<Booking> getByUser(long userId);

  List<Booking> findBookingsUntilFor(ZonedDateTime endTime, Set<Long> users);

}
