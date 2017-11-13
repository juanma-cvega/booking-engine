package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.Booking;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.slot.SlotResourceFactory;

import java.util.List;

import static com.jusoft.bookingengine.util.TimeUtil.getTimeFrom;
import static java.util.stream.Collectors.toList;

class BookingResourceFactory {

  private final SlotResourceFactory slotResourceFactory;

  BookingResourceFactory(SlotResourceFactory slotResourceFactory) {
    this.slotResourceFactory = slotResourceFactory;
  }

  BookingResource createFrom(Booking booking) {
    return new BookingResource(booking.getId(), getTimeFrom(booking.getBookingTime()), slotResourceFactory.createFrom(booking.getSlot()));
  }

  BookingResources createFrom(List<Booking> bookings) {
    List<BookingResource> bookingViews = bookings.stream().map(this::createFrom).collect(toList());
    return new BookingResources(bookingViews);
  }
}
