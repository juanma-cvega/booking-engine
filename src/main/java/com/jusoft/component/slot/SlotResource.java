package com.jusoft.component.slot;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class SlotResource {

    private final long slotId;
    private final long roomId;
    private final long startDate;
    private final long endDate;

    public SlotResource(long slotId, long roomId, long startDate, long endDate) {
        this.slotId = slotId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private SlotResource() {
        this.slotId = 0;
        this.roomId = 0;
        this.startDate = 0;
        this.endDate = 0;
    }

    public long getSlotId() {
        return slotId;
    }

    public long getRoomId() {
        return roomId;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
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
