package com.jusoft.component.booking;

import java.util.ArrayList;
import java.util.List;

public class BookingHolder {

    public BookingResource bookingCreated;
    public BookingResource bookingFetched;
    public List<BookingResource> bookingsCreated = new ArrayList<>();
    public List<BookingResource> bookingsFetched = new ArrayList<>();
}
