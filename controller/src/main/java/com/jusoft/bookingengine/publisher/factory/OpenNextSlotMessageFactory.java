package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.publisher.message.OpenNextSlotMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenNextSlotMessageFactory implements MessageFactory<OpenNextSlotCommand, OpenNextSlotMessage> {
  @Override
  public OpenNextSlotMessage createFrom(OpenNextSlotCommand message) {
    return new OpenNextSlotMessage(
      message.getRoomId(),
      message.getClubId());
  }
}
