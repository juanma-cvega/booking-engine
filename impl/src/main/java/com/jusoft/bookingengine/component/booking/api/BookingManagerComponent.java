package com.jusoft.bookingengine.component.booking.api;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface BookingManagerComponent {

  BookingView book(CreateBookingCommand createBookingCommand);

  void cancel(long userId, long bookingId);

  BookingView find(long bookingId);

  List<BookingView> findAllBy(long userId);

  List<BookingView> getFor(long userId);

  List<BookingView> findUsersBookingsUntilFor(ZonedDateTime endTime, Set<Long> users);
}
