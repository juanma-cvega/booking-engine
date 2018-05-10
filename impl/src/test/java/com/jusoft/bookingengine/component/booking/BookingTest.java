package com.jusoft.bookingengine.component.booking;

import org.junit.Test;

import static com.jusoft.bookingengine.fixture.BookingFixtures.BOOKING_ID;
import static com.jusoft.bookingengine.fixture.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixture.SlotFixtures.SLOT_ID_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BookingTest {

  @Test
  public void booking_without_booking_time_fails_creation() {
    assertThatThrownBy(() -> new Booking(BOOKING_ID, USER_ID_1, null, SLOT_ID_1))
      .isInstanceOf(NullPointerException.class);
  }
}
