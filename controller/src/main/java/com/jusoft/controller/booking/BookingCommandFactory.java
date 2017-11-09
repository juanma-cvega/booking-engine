package com.jusoft.controller.booking;

import com.jusoft.component.booking.api.CancelBookingCommand;
import com.jusoft.component.booking.api.CreateBookingCommand;

class BookingCommandFactory {

  public CancelBookingCommand createFrom(long userId, long bookingId) {
    return new CancelBookingCommand(userId, bookingId);
  }

  public CreateBookingCommand createFrom(long roomId, long slotId, long userId) {
    return new CreateBookingCommand(userId, slotId, roomId);
  }
}
