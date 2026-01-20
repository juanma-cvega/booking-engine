package com.jusoft.bookingengine.controller.booking;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BookingResources {

    private final List<BookingResource> bookings;

    public BookingResources(List<BookingResource> bookings) {
        this.bookings = bookings;
    }

    @JsonCreator
    private BookingResources() {
        bookings = null;
    }

    public List<BookingResource> getBookings() {
        return bookings;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JSON_STYLE);
    }
}
