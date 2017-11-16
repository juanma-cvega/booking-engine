package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.component.booking.Booking;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface BookingComponent {

  Booking book(CreateBookingCommand createBookingCommand) throws SlotAlreadyStartedException;

  void cancel(CancelBookingCommand cancelBookingCommand) throws BookingNotFoundException;

  Booking find(long userId, long bookingId) throws BookingNotFoundException;

  List<Booking> getFor(long userId);

  List<Long> findUsersWithLessBookingsUntil(ZonedDateTime endTime, Set<Long> users);
}
