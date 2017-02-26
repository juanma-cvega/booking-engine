package com.jusoft.controller.slot;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class CreateSlotRequest {

    @NotNull
    @Min(1)
    private final Long roomId;
    @NotNull
    @Min(1)
    private final Long startTime;
    @NotNull
    @Min(1)
    private final Long endTime;

    public CreateSlotRequest(Long roomId, Long startTime, Long endTime) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @JsonCreator
    private CreateSlotRequest() {
        this.roomId = null;
        this.startTime = null;
        this.endTime = null;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
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
