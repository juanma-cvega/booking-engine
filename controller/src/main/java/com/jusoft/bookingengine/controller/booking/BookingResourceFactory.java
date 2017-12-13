package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.slot.SlotResourceFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

class BookingResourceFactory {

  private final SlotResourceFactory slotResourceFactory;

  BookingResourceFactory(SlotResourceFactory slotResourceFactory) {
    this.slotResourceFactory = slotResourceFactory;
  }

  BookingResource createFrom(BookingView booking) {
//    return new BookingResource(booking.getId(), getTimeFrom(booking.getBookingTime()), slotResourceFactory.createFrom(booking.getSlot()));
    return null;
  }

  BookingResources createFrom(List<BookingView> bookings) {
    List<BookingResource> bookingViews = bookings.stream().map(this::createFrom).collect(toList());
    return new BookingResources(bookingViews);
  }
}
