package com.jusoft.bookingengine.holder;

import com.jusoft.bookingengine.component.booking.api.BookingView;

import java.util.ArrayList;
import java.util.List;

public class BookingHolder {

  public BookingView bookingCreated;
  public List<BookingView> bookingsCreated = new ArrayList<>();
  public List<BookingView> bookingsFetched = new ArrayList<>();
}
