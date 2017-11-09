package com.jusoft.component.booking.api;

import com.jusoft.component.booking.Booking;

import java.util.List;

public interface BookingComponent {

  Booking book(CreateBookingCommand createBookingCommand) throws SlotAlreadyStartedException;

  void cancel(CancelBookingCommand cancelBookingCommand) throws BookingNotFoundException;

  Booking find(long userId, long bookingId) throws BookingNotFoundException;

  List<Booking> getFor(long userId);
}
