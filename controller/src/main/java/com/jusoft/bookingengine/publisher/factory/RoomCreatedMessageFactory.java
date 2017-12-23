package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.publisher.message.RoomCreatedMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomCreatedMessageFactory implements MessageFactory<RoomCreatedEvent, RoomCreatedMessage> {
  @Override
  public RoomCreatedMessage createFrom(RoomCreatedEvent message) {
    return new RoomCreatedMessage(
      message.getRoomId(),
      message.getMaxSlots(),
      message.getSlotDurationInMinutes(),
      message.getOpenTimesPerDay(),
      message.getAvailableDays(),
      message.isActive());
  }
}
