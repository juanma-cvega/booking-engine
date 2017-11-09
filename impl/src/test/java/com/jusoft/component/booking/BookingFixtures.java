package com.jusoft.component.booking;

import com.jusoft.component.booking.api.CreateBookingCommand;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_1;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_2;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_ID_1;

@UtilityClass
public class BookingFixtures {

  public static final ZonedDateTime BOOKING_TIME = ZonedDateTime.now();
  public static final long BOOKING_ID_1 = 44;
  public static final long BOOKING_ID_2 = 66;

  public static final Booking BOOKING_1 = new Booking(BOOKING_ID_1, USER_ID_1, BOOKING_TIME, SLOT_1);
  public static final Booking BOOKING_2 = new Booking(BOOKING_ID_2, USER_ID_1, BOOKING_TIME, SLOT_2);

  public static final CreateBookingCommand CREATE_BOOKING_COMMAND = new CreateBookingCommand(USER_ID_1, ROOM_ID, SLOT_ID_1);
}
