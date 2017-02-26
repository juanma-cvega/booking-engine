package com.jusoft.controller.booking;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class CreateBookingRequest {

    @NotNull
    @Min(1)
    private final Long userId;
    @NotNull
    @Min(1)
    private final Long roomId;
    @NotNull
    @Min(1)
    private final Long slotId;

    public CreateBookingRequest(Long userId, Long roomId, Long slotId) {
        this.userId = userId;
        this.roomId = roomId;
        this.slotId = slotId;
    }

    @JsonCreator
    private CreateBookingRequest() {
        this.userId = null;
        this.roomId = null;
        this.slotId = null;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getSlotId() {
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
