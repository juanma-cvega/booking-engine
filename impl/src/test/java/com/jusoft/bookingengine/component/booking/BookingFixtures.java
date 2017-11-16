package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.fixtures.CommonFixtures;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.bookingengine.component.slot.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.component.slot.SlotsFixtures.SLOT_ID_2;

@UtilityClass
public class BookingFixtures {

  public static final ZonedDateTime BOOKING_TIME = ZonedDateTime.now();
  public static final long BOOKING_ID_1 = 44;
  public static final long BOOKING_ID_2 = 66;

  public static final Booking BOOKING_1 = new Booking(BOOKING_ID_1, CommonFixtures.USER_ID_1, BOOKING_TIME, SLOT_ID_1, ROOM_ID);
  public static final Booking BOOKING_2 = new Booking(BOOKING_ID_2, CommonFixtures.USER_ID_1, BOOKING_TIME, SLOT_ID_2, ROOM_ID);

  public static final CreateBookingCommand CREATE_BOOKING_COMMAND = new CreateBookingCommand(CommonFixtures.USER_ID_1, ROOM_ID, SLOT_ID_1);
}
