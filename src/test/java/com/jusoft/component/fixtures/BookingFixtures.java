package com.jusoft.component.fixtures;

import com.jusoft.component.booking.BookingResource;
import com.jusoft.component.booking.BookingResources;
import com.jusoft.component.booking.CreateBookingRequest;

import java.time.Instant;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.component.fixtures.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.component.fixtures.SlotsFixtures.SLOT_RESOURCE;
import static java.util.Arrays.asList;

public class BookingFixtures {

    public static final long BOOKING_TIME = Instant.now().getEpochSecond();
    public static final long BOOKING_ID_1 = 44;
    public static final long BOOKING_ID_2 = 66;
    public static final BookingResource BOOKING_RESOURCE_1 = new BookingResource(BOOKING_ID_1, BOOKING_TIME, SLOT_RESOURCE);
    public static final BookingResource BOOKING_RESOURCE_2 = new BookingResource(BOOKING_ID_2, BOOKING_TIME, SLOT_RESOURCE);
    public static final BookingResources BOOKING_RESOURCES = new BookingResources(asList(BOOKING_RESOURCE_1, BOOKING_RESOURCE_2));

    public static final CreateBookingRequest CREATE_BOOKING_REQUEST = new CreateBookingRequest(USER_ID_1, ROOM_ID, SLOT_ID_1);
}
