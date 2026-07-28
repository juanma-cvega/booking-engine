package com.jusoft.bookingengine.controller.booking;

import static java.util.stream.Collectors.toList;

import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import java.util.List;

class BookingResourceFactory {

    BookingResource createFrom(BookingView booking) {
        return null;
    }

    BookingResources createFrom(List<BookingView> bookings) {
        List<BookingResource> bookingViews =
                bookings.stream().map(this::createFrom).collect(toList());
        return new BookingResources(bookingViews);
    }
}
