package com.jusoft.component.booking;

import com.jusoft.controller.booking.BookingResource;
import com.jusoft.controller.booking.BookingResources;
import com.jusoft.controller.booking.CreateBookingRequest;
import com.jusoft.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;

import static com.jusoft.component.slot.SlotFixtures.*;
import static com.jusoft.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.fixtures.CommonFixtures.USER_ID_1;
import static java.util.Arrays.asList;

public class BookingFixtures {

    public static final long BOOKING_ID_1 = 44;
    public static final long BOOKING_ID_2 = 66;
    public static final long BOOKING_TIME = 123455678;
    public static final LocalDateTime BOOKING_TIME_DATE = TimeUtil.getLocalDateTimeFrom(BOOKING_TIME);
    public static final CreateBookingRequest CREATE_BOOKING_REQUEST = new CreateBookingRequest(USER_ID_1, ROOM_ID, SLOT_ID_1);
    public static final CancelBookingCommand CANCEL_BOOKING_COMMAND = new CancelBookingCommand(USER_ID_1, BOOKING_ID_1);

    public static final Booking BOOKING_1 = new Booking(BOOKING_ID_1, USER_ID_1, BOOKING_TIME_DATE, SLOT_1);
    public static final Booking BOOKING_2 = new Booking(BOOKING_ID_2, USER_ID_1, BOOKING_TIME_DATE, SLOT_2);
    public static final List<Booking> BOOKINGS = asList(BOOKING_1, BOOKING_2);

    public static final CreateBookingCommand CREATE_BOOKING_COMMAND = new CreateBookingCommand(USER_ID_1, ROOM_ID, SLOT_ID_1);

    public static final BookingResource BOOKING_RESOURCE_1 = new BookingResource(BOOKING_ID_1, BOOKING_TIME, SLOT_RESOURCE_1);
    public static final BookingResource BOOKING_RESOURCE_2 = new BookingResource(BOOKING_ID_2, BOOKING_TIME, SLOT_RESOURCE_1);
    public static final BookingResources BOOKING_RESOURCES = new BookingResources(asList(BOOKING_RESOURCE_1, BOOKING_RESOURCE_2));
}
