package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.usecase.OpenNextSlotUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenNextSlotCommandListener implements MessageListener {

  private final OpenNextSlotUseCase openNextSlotUseCase;

  @EventListener(OpenNextSlotCommand.class)
  public void openNextSlot(OpenNextSlotCommand command) {
    log.info("OpenNextSlotCommand consumed: roomId={}", command.getRoomId());
    SlotView slotCreated = openNextSlotUseCase.openNextSlotFor(command.getRoomId());
    log.info("OpenNextSlotCommand processed. Slot created: slotId={}, roomId={}, startDate={}, endDate={}",
      slotCreated.getId(), slotCreated.getRoomId(), slotCreated.getStartDate(), slotCreated.getEndDate());
  }
}
