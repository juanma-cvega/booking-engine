package com.jusoft.bookingengine.component.booking.api;

public class WrongBookingUserException extends RuntimeException {

  private static final String MESSAGE = "The provided user %s, does not match the expected user %s for booking %s";

  public WrongBookingUserException(long providedId, long expectedId, long bookingId) {
    super(String.format(MESSAGE, providedId, expectedId, bookingId));
  }
}
