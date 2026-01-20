package com.jusoft.bookingengine.controller.booking;

import static java.util.stream.Collectors.toList;

import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.slot.SlotResourceFactory;
import java.util.List;

class BookingResourceFactory {

    private final SlotResourceFactory slotResourceFactory;

    BookingResourceFactory(SlotResourceFactory slotResourceFactory) {
        this.slotResourceFactory = slotResourceFactory;
    }

    BookingResource createFrom(BookingView booking) {
        return null;
    }

    BookingResources createFrom(List<BookingView> bookings) {
        List<BookingResource> bookingViews =
                bookings.stream().map(this::createFrom).collect(toList());
        return new BookingResources(bookingViews);
    }
}
