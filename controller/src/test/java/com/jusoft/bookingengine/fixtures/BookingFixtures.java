package com.jusoft.bookingengine.fixtures;

import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_ID_1;
import static java.util.Arrays.asList;

import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.controller.booking.BookingResources;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.booking.api.CreateBookingRequest;
import com.jusoft.bookingengine.util.TimeUtil;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingFixtures {

    public static final long BOOKING_ID_1 = 44;
    public static final long BOOKING_ID_2 = 66;

    public static final long BOOKING_TIME = 123455678;
    public static final ZonedDateTime BOOKING_TIME_DATE =
            TimeUtil.getLocalDateTimeFrom(BOOKING_TIME);

    public static final BookingView BOOKING_1 =
            BookingView.of(BOOKING_ID_1, USER_ID_1, BOOKING_TIME_DATE, SlotFixtures.SLOT_ID_1);
    public static final BookingView BOOKING_2 =
            BookingView.of(BOOKING_ID_2, USER_ID_1, BOOKING_TIME_DATE, SlotFixtures.SLOT_ID_2);
    public static final List<BookingView> BOOKINGS = asList(BOOKING_1, BOOKING_2);

    public static final CreateBookingCommand CREATE_BOOKING_COMMAND =
            CreateBookingCommand.of(USER_ID_1, SlotFixtures.SLOT_ID_1);

    public static final BookingResource BOOKING_RESOURCE_1 =
            new BookingResource(BOOKING_ID_1, BOOKING_TIME, SlotFixtures.SLOT_RESOURCE_1);
    public static final BookingResource BOOKING_RESOURCE_2 =
            new BookingResource(BOOKING_ID_2, BOOKING_TIME, SlotFixtures.SLOT_RESOURCE_1);
    public static final BookingResources BOOKING_RESOURCES =
            new BookingResources(asList(BOOKING_RESOURCE_1, BOOKING_RESOURCE_2));

    public static final CreateBookingRequest CREATE_BOOKING_REQUEST =
            new CreateBookingRequest(USER_ID_1);
    public static final BookingCreatedEvent BOOKING_CREATED_EVENT =
            BookingCreatedEvent.of(BOOKING_ID_1, USER_ID_1, SLOT_ID_1);
}
