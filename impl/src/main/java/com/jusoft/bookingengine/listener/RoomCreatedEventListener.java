package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.usecase.SlotUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class RoomCreatedEventListener implements MessageListener<RoomCreatedEvent> {

  private final SlotUseCase slotUseCase;

  @EventListener(RoomCreatedEvent.class)
  @Override
  public void consume(RoomCreatedEvent event) {
    log.info("RoomCreatedEvent consumed: roomId={}", event.getRoomId());
    slotUseCase.openNextSlotFor(event.getRoomId());
  }
}
