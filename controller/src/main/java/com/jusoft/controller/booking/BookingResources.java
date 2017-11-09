package com.jusoft.controller.booking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jusoft.controller.booking.api.BookingResource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

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
