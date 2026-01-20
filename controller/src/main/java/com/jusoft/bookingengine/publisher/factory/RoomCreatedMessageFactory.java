package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.publisher.message.RoomCreatedMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomCreatedMessageFactory implements MessageFactory<RoomCreatedEvent, RoomCreatedMessage> {
    @Override
    public RoomCreatedMessage createFrom(RoomCreatedEvent message) {
        return RoomCreatedMessage.of(
                message.getRoomId(),
                message.getSlotDurationInMinutes(),
                message.getOpenTimesPerDay(),
                message.getAvailableDays());
    }
}
