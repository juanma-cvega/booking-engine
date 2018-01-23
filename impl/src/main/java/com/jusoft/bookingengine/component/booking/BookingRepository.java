package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.repository.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

interface BookingRepository extends Repository<Booking> {

  void save(Booking newBooking);

  void delete(long bookingId, Predicate<Booking> predicate);

  Optional<Booking> find(long bookingId);

  List<Booking> getByUser(long userId);

  List<Booking> findBookingsUntilFor(ZonedDateTime endTime, Set<Long> users);

}
