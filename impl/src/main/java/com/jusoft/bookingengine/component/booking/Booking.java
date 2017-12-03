package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Booking {

  private final long id;
  private final long userId;
  @NonNull
  private final ZonedDateTime bookingTime;
  private final long slotId;
  private final long roomId;

  boolean canClose(Long requestUserId) {
    validateUserOwnsBooking(requestUserId);
    return true;
  }

  private void validateUserOwnsBooking(Long requestUserId) {
    if (Long.compare(requestUserId, userId) != 0) {
      throw new WrongBookingUserException(userId, userId, id);
    }
  }
}
