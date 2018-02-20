package com.jusoft.bookingengine.component.booking;


import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingFactory {

  private final Supplier<Long> idGenerator;
  private final Clock clock;

  Booking create(CreateBookingCommand createBookingCommand) {
    return new Booking(idGenerator.get(),
      createBookingCommand.getUserId(),
      ZonedDateTime.now(clock),
      createBookingCommand.getSlotId());
  }

  BookingView create(Booking booking) {
    return BookingView.of(booking.getId(),
      booking.getUserId(),
      booking.getBookingTime(),
      booking.getSlotId());
  }

  public List<BookingView> create(List<Booking> bookings) {
    return bookings.stream().map(this::create).collect(toList());
  }
}
