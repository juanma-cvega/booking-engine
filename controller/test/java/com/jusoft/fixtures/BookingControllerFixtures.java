package com.jusoft.fixtures;

import com.jusoft.component.booking.CancelBookingCommand;
import com.jusoft.controller.booking.BookingResource;
import com.jusoft.controller.booking.BookingResources;
import com.jusoft.controller.booking.CreateBookingRequest;

import static com.jusoft.component.booking.BookingFixtures.BOOKING_ID_1;
import static com.jusoft.component.booking.BookingFixtures.BOOKING_ID_2;
import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.fixtures.SlotControllerFixtures.SLOT_RESOURCE_1;
import static java.util.Arrays.asList;

public class BookingControllerFixtures {

    public static final long BOOKING_TIME = 123455678;
    public static final CreateBookingRequest CREATE_BOOKING_REQUEST = new CreateBookingRequest(USER_ID_1, ROOM_ID, SLOT_ID_1);
    public static final CancelBookingCommand CANCEL_BOOKING_COMMAND = new CancelBookingCommand(USER_ID_1, BOOKING_ID_1);

    public static final BookingResource BOOKING_RESOURCE_1 = new BookingResource(BOOKING_ID_1, BOOKING_TIME, SLOT_RESOURCE_1);
    public static final BookingResource BOOKING_RESOURCE_2 = new BookingResource(BOOKING_ID_2, BOOKING_TIME, SLOT_RESOURCE_1);
    public static final BookingResources BOOKING_RESOURCES = new BookingResources(asList(BOOKING_RESOURCE_1, BOOKING_RESOURCE_2));
}
