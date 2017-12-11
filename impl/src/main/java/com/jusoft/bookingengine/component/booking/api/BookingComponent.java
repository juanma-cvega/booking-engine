package com.jusoft.bookingengine.component.booking.api;

import com.jusoft.bookingengine.component.booking.Booking;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface BookingComponent {

  Booking book(CreateBookingCommand createBookingCommand);

  void cancel(CancelBookingCommand cancelBookingCommand);

  Booking find(long userId, long bookingId);

  List<Booking> findAllBy(long userId);

  List<Booking> getFor(long userId);

  List<Booking> findUsersBookingsUntilFor(ZonedDateTime endTime, Set<Long> users);
}
