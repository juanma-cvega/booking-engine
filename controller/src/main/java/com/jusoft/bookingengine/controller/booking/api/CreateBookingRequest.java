package com.jusoft.bookingengine.controller.booking.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class CreateBookingRequest {

  @NotNull
  private final Long userId;

  private CreateBookingRequest() {
    userId = null;
  }

  public CreateBookingRequest(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
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
