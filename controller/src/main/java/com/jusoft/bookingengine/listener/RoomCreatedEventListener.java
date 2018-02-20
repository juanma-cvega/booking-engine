package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.publisher.message.RoomCreatedMessage;
import com.jusoft.bookingengine.usecase.CreateSlotUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class RoomCreatedEventListener implements MessageListener {

  private final CreateSlotUseCase openNextSlotUseCase;

  @EventListener(RoomCreatedMessage.class)
  public void openFirstSlot(RoomCreatedMessage event) {
    log.info("RoomCreatedEvent consumed: roomId={}", event.getRoomId());
    SlotView slotCreated = openNextSlotUseCase.createSlotFor(OpenNextSlotCommand.of(event.getRoomId()));
    log.info("RoomCreatedEvent processed: roomId={}, slotId={} ", slotCreated.getRoomId(), slotCreated.getId());
  }
}
