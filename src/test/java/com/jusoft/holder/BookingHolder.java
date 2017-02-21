package com.jusoft.holder;

import com.jusoft.booking.BookingResource;

import java.util.ArrayList;
import java.util.List;

public class BookingHolder {

    public BookingResource bookingCreated;
    public BookingResource bookingFetched;
    public List<BookingResource> bookingsCreated = new ArrayList<>();
    public List<BookingResource> bookingsFetched = new ArrayList<>();
}