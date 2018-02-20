package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Booking %s not found for user %s";

  private final long userId;
  private final long bookingId;

  public BookingNotFoundException(long userId, long bookingId) {
    super(String.format(MESSAGE, bookingId, userId));
    this.userId = userId;
    this.bookingId = bookingId;
  }
}
