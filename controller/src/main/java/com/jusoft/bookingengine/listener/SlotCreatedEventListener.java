package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.publisher.message.SlotCreatedMessage;
import com.jusoft.bookingengine.usecase.slot.ScheduleNextSlotUseCase;
import com.jusoft.bookingengine.usecase.slotlifecycle.FindNextSlotStateUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotCreatedEventListener implements MessageListener {

    private final ScheduleNextSlotUseCase scheduleNextSlotUseCase;
    private final FindNextSlotStateUseCase findNextSlotStateUseCase;

    @EventListener(SlotCreatedMessage.class)
    public void scheduleNextSlot(SlotCreatedMessage event) {
        log.info(
                "SlotCreatedEvent consumed: slotId={}, roomId={}, openDate={}",
                event.getSlotId(),
                event.getRoomId(),
                event.getOpenDate());
        scheduleNextSlotUseCase.scheduleNextSlot(event.getRoomId());
    }

    @EventListener(SlotCreatedMessage.class)
    public void startAuction(SlotCreatedMessage event) {
        log.info(
                "SlotCreatedEvent consumed for auction: slotId={}, roomId={}, openDate={}, endTime={}",
                event.getSlotId(),
                event.getRoomId(),
                event.getOpenDate());
        findNextSlotStateUseCase.findNextSlotStateUseCase(
                event.getSlotId(), event.getRoomId(), event.getOpenDate().getStartTime());
    }
}
