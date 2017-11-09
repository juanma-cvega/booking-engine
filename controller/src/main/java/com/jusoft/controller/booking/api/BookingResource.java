package com.jusoft.controller.booking.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jusoft.controller.slot.SlotResource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class BookingResource {

  private final long bookingId;
  private final long bookingTime;
  private final SlotResource slot;

  public BookingResource(long bookingId, long bookingTime, SlotResource slot) {
    this.bookingId = bookingId;
    this.bookingTime = bookingTime;
    this.slot = slot;
  }

  @JsonCreator
  private BookingResource() {
    bookingId = 0;
    bookingTime = 0;
    slot = null;
  }

  public long getBookingId() {
    return bookingId;
  }

  public long getBookingTime() {
    return bookingTime;
  }

  public SlotResource getSlot() {
    return slot;
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
