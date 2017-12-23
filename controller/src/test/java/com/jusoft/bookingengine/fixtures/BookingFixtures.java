package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.controller.booking.BookingResources;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.booking.api.CreateBookingRequest;
import com.jusoft.bookingengine.util.TimeUtil;

import java.time.ZonedDateTime;
import java.util.List;

import static com.jusoft.bookingengine.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_1;
import static java.util.Arrays.asList;

public class BookingFixtures {

  public static final long BOOKING_ID_1 = 44;
  public static final long BOOKING_ID_2 = 66;

  public static final long BOOKING_TIME = 123455678;
  public static final ZonedDateTime BOOKING_TIME_DATE = TimeUtil.getLocalDateTimeFrom(BOOKING_TIME);
  public static final CancelBookingCommand CANCEL_BOOKING_COMMAND = new CancelBookingCommand(USER_ID_1, BOOKING_ID_1);

  public static final BookingView BOOKING_1 = new BookingView(BOOKING_ID_1, USER_ID_1, BOOKING_TIME_DATE, SlotFixtures.SLOT_ID_1, ROOM_ID);
  public static final BookingView BOOKING_2 = new BookingView(BOOKING_ID_2, USER_ID_1, BOOKING_TIME_DATE, SlotFixtures.SLOT_ID_2, ROOM_ID);
  public static final List<BookingView> BOOKINGS = asList(BOOKING_1, BOOKING_2);

  public static final CreateBookingCommand CREATE_BOOKING_COMMAND = new CreateBookingCommand(USER_ID_1, ROOM_ID, SlotFixtures.SLOT_ID_1);

  public static final BookingResource BOOKING_RESOURCE_1 = new BookingResource(BOOKING_ID_1, BOOKING_TIME, SlotFixtures.SLOT_RESOURCE_1);
  public static final BookingResource BOOKING_RESOURCE_2 = new BookingResource(BOOKING_ID_2, BOOKING_TIME, SlotFixtures.SLOT_RESOURCE_1);
  public static final BookingResources BOOKING_RESOURCES = new BookingResources(asList(BOOKING_RESOURCE_1, BOOKING_RESOURCE_2));

  public static final CreateBookingRequest CREATE_BOOKING_REQUEST = new CreateBookingRequest(USER_ID_1);

}
