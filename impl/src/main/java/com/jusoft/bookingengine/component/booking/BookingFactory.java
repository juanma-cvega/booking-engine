package com.jusoft.bookingengine.component.booking;


import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingFactory {

  private final Supplier<Long> idGenerator;
  private final Clock clock;

  Booking create(CreateBookingCommand createBookingCommand) {
    return new Booking(idGenerator.get(),
      createBookingCommand.getUserId(),
      ZonedDateTime.now(clock),
      createBookingCommand.getSlotId(),
      createBookingCommand.getRoomId());
  }
}
