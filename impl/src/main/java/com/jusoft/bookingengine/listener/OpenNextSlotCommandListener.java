package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.usecase.SlotUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenNextSlotCommandListener implements MessageListener {

  private final SlotUseCase slotUseCase;

  @EventListener(OpenNextSlotCommand.class)
  public void openNextSlot(OpenNextSlotCommand command) {
    log.info("OpenNextSlotCommand consumed: roomId={}", command.getRoomId());
    slotUseCase.openNextSlotFor(command.getRoomId());
  }
}
