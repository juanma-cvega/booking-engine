package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.room.api.SlotRequiredEvent;
import com.jusoft.bookingengine.publisher.message.SlotRequiredMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotRequiredMessageFactory implements MessageFactory<SlotRequiredEvent, SlotRequiredMessage> {

  @Override
  public SlotRequiredMessage createFrom(SlotRequiredEvent message) {
    return SlotRequiredMessage.of(
      message.getRoomId());
  }
}
