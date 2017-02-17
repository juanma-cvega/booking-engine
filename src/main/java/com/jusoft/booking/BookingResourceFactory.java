package com.jusoft.booking;

import static com.jusoft.util.TimeUtil.getTimeFrom;

class BookingResourceFactory {

    private final SlotResourceFactory slotResourceFactory;

    public BookingResourceFactory(SlotResourceFactory slotResourceFactory) {
        this.slotResourceFactory = slotResourceFactory;
    }

    BookingResource createFrom(Booking booking) {
        return new BookingResource(booking.getBookingId(), booking.getUserId(), getTimeFrom(booking.getBookingTime()), slotResourceFactory.createFrom(booking.getSlot()));
    }
}
