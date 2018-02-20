package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.publisher.message.SlotCreatedMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotCreatedMessageFactory implements MessageFactory<SlotCreatedEvent, SlotCreatedMessage> {
  @Override
  public SlotCreatedMessage createFrom(SlotCreatedEvent message) {
    return SlotCreatedMessage.of(
      message.getSlotId(),
      message.getRoomId(),
      message.getState(),
      message.getOpenDate());
  }
}
