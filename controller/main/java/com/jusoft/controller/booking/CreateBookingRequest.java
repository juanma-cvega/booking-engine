package com.jusoft.controller.booking;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class CreateBookingRequest {

    private final long userId;
    private final long roomId;
    private final long slotId;

    public CreateBookingRequest(long userId, long roomId, long slotId) {
        this.userId = userId;
        this.roomId = roomId;
        this.slotId = slotId;
    }

    @JsonCreator
    private CreateBookingRequest() {
        this.userId = 0;
        this.roomId = 0;
        this.slotId = 0;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public long getSlotId() {
        return slotId;
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
