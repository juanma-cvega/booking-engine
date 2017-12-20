package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.usecase.ScheduleNextSlotUseCase;
import com.jusoft.bookingengine.usecase.StartAuctionUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SlotCreatedEventListener implements MessageListener {

  private final ScheduleNextSlotUseCase scheduleNextSlotUseCase;
  private final StartAuctionUseCase startAuctionUseCase;

  @EventListener(SlotCreatedEvent.class)
  public void scheduleNextSlot(SlotCreatedEvent event) {
    log.info("SlotCreatedEvent consumed: slotId={}, roomId={}, startTime={}, endTime={}",
      event.getSlotId(), event.getRoomId(), event.getStartDate(), event.getEndDate());
    scheduleNextSlotUseCase.scheduleNextSlot(event.getRoomId());
  }

  //FIXME add auction configuration to event to avoid calling the roomComponent???
  @EventListener(SlotCreatedEvent.class)
  public void startAuction(SlotCreatedEvent event) {
    log.info("SlotCreatedEvent consumed for auction: slotId={}, roomId={}, startTime={}, endTime={}",
      event.getSlotId(), event.getRoomId(), event.getStartDate(), event.getEndDate());
    startAuctionUseCase.startAuction(event.getRoomId(), event.getSlotId());
  }
}
