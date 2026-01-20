package com.jusoft.bookingengine.component.authorization.api;

import lombok.Getter;

@Getter
public class UnauthorizedReservationException extends RuntimeException {

    private static final long serialVersionUID = -6992247035718545745L;

    private static final String MESSAGE =
            "User %s is not authorized to reserve slot with status %s for club %s, building %s and room %s";

    private final long userId;
    private final long clubId;
    private final long buildingId;
    private final long roomId;
    private final SlotStatus slotStatus;

    public UnauthorizedReservationException(
            long userId, long clubId, long buildingId, long roomId, SlotStatus slotStatus) {
        super(String.format(MESSAGE, userId, slotStatus, clubId, buildingId, roomId));
        this.userId = userId;
        this.clubId = clubId;
        this.buildingId = buildingId;
        this.roomId = roomId;
        this.slotStatus = slotStatus;
    }
}
