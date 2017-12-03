package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.usecase.AuctionUseCase;
import com.jusoft.bookingengine.usecase.SlotUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SlotCreatedEventListener implements MessageListener<SlotCreatedEvent> {

  private final SlotUseCase slotUseCase;
  private final AuctionUseCase auctionUseCase;

  @EventListener(SlotCreatedEvent.class)
  @Override
  public void consume(SlotCreatedEvent event) {
    log.info("SlotCreatedEvent consumed: slotId={}, roomId={}, startTime={}, endTime={}",
      event.getSlotId(), event.getRoomId(), event.getStartTime(), event.getEndTime());
    slotUseCase.scheduleNextSlot(event.getRoomId());
  }

  //FIXME decide between one method per listener or remove the method signature from the interface
  @EventListener(SlotCreatedEvent.class)
  public void consume2(SlotCreatedEvent event) {
    log.info("SlotCreatedEvent consumed for auction: slotId={}, roomId={}, startTime={}, endTime={}",
      event.getSlotId(), event.getRoomId(), event.getStartTime(), event.getEndTime());
    auctionUseCase.startAuction(event.getRoomId(), event.getSlotId());
  }
}
