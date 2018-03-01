package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.publisher.message.SlotRequiredMessage;
import com.jusoft.bookingengine.usecase.slot.CreateSlotUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotRequiredEventListener implements MessageListener {

  private final CreateSlotUseCase createSlotUseCase;

  @EventListener(SlotRequiredMessage.class)
  public void slotRequired(SlotRequiredMessage command) {
    log.info("OpenNextSlotCommand consumed: roomId={}", command.getRoomId());
    SlotView slotCreated = createSlotUseCase.createSlotFor(command.getRoomId());
    log.info("OpenNextSlotCommand processed. Slot created: slotId={}, roomId={}, startDate={}, endDate={}",
      slotCreated.getId(), slotCreated.getRoomId(), slotCreated.getOpenDate().getStartTime(), slotCreated.getOpenDate().getEndTime());
  }
}
