package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SlotCreatedEventListener implements MessageListener<SlotCreatedEvent> {

  private final RoomComponent roomComponent;

  @EventListener(SlotCreatedEvent.class)
  @Override
  public void consume(SlotCreatedEvent event) {
    log.info("SlotCreatedEvent consumed: slotId={}, roomId={}, startTime={}, endTime={}",
      event.getSlotId(), event.getRoomId(), event.getStartTime(), event.getEndTime());
    roomComponent.scheduleComingSlotFor(event.getRoomId());
  }
}
