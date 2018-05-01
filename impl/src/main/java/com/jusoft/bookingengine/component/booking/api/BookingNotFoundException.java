package com.jusoft.bookingengine.component.booking.api;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8811144902223456044L;

  private static final String MESSAGE = "Booking %s not found";

  private final long bookingId;

  public BookingNotFoundException(long bookingId) {
    super(String.format(MESSAGE, bookingId));
    this.bookingId = bookingId;
  }
}
