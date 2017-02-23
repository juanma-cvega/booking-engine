package com.jusoft.booking;

import java.util.List;

import static com.jusoft.util.TimeUtil.getTimeFrom;
import static java.util.stream.Collectors.toList;

class BookingResourceFactory {

    private final SlotResourceFactory slotResourceFactory;

    public BookingResourceFactory(SlotResourceFactory slotResourceFactory) {
        this.slotResourceFactory = slotResourceFactory;
    }

    BookingResource createFrom(Booking booking) {
        return new BookingResource(booking.getBookingId(), getTimeFrom(booking.getBookingTime()), slotResourceFactory.createFrom(booking.getSlot()));
    }

    BookingResources createFrom(List<Booking> bookings) {
        List<BookingResource> bookingResources = bookings.stream().map(this::createFrom).collect(toList());
        return new BookingResources(bookingResources);
    }
}
