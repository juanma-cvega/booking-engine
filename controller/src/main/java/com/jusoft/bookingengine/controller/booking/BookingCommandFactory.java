package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;

class BookingCommandFactory {

  public CancelBookingCommand createFrom(long userId, long bookingId) {
    return CancelBookingCommand.of(userId, bookingId);
  }

  public CreateBookingCommand createFrom(long roomId, long slotId, long userId) {
    return CreateBookingCommand.of(userId, slotId);
  }
}
