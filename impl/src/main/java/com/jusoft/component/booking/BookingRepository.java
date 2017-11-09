package com.jusoft.component.booking;

import com.jusoft.component.booking.api.SlotAlreadyBookedException;

import java.util.List;
import java.util.Optional;

interface BookingRepository {

  void save(Booking newBooking) throws SlotAlreadyBookedException;

  void delete(long bookingId);

  Optional<Booking> find(long bookingId);

  List<Booking> getByUser(long userId);
}
