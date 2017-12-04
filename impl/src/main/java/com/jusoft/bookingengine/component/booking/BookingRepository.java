package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.SlotAlreadyBookedException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

interface BookingRepository {

  void save(Booking newBooking) throws SlotAlreadyBookedException;

  void delete(long bookingId);

  Optional<Booking> find(long bookingId);

  List<Booking> getByUser(long userId);

  Set<Long> findUserWithLessBookingsUntil(ZonedDateTime endTime, Set<Long> users);
}