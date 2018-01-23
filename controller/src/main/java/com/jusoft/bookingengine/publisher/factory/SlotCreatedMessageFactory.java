package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.publisher.message.SlotCreatedMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotCreatedMessageFactory implements MessageFactory<SlotCreatedEvent, SlotCreatedMessage> {
  @Override
  public SlotCreatedMessage createFrom(SlotCreatedEvent message) {
    return new SlotCreatedMessage(
      message.getSlotId(),
      message.getRoomId(),
      message.getClubId(),
      message.getStartDate(),
      message.getEndDate());
  }
}
