package com.jusoft.component.booking;

import java.time.LocalDateTime;
import java.util.List;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.component.slot.SlotsFixtures.*;
import static java.util.Arrays.asList;

public class BookingFixtures {

    public static final LocalDateTime BOOKING_TIME = LocalDateTime.now();
    public static final long BOOKING_ID_1 = 44;
    public static final long BOOKING_ID_2 = 66;

    public static final Booking BOOKING_1 = new Booking(BOOKING_ID_1, USER_ID_1, BOOKING_TIME, SLOT_1);
    public static final Booking BOOKING_2 = new Booking(BOOKING_ID_2, USER_ID_1, BOOKING_TIME, SLOT_2);
    public static final List<Booking> BOOKINGS = asList(BOOKING_1, BOOKING_2);

    public static final CreateBookingCommand CREATE_BOOKING_COMMAND = new CreateBookingCommand(USER_ID_1, ROOM_ID, SLOT_ID_1);
}
