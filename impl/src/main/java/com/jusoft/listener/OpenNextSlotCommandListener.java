package com.jusoft.listener;

import com.jusoft.component.room.api.OpenNextSlotCommand;
import com.jusoft.component.room.api.RoomComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenNextSlotCommandListener implements MessageListener<OpenNextSlotCommand> {

  private final RoomComponent roomComponent;

  @EventListener(OpenNextSlotCommand.class)
  @Override
  public void consume(OpenNextSlotCommand command) {
    log.info("OpenNextSlotCommand consumed: roomId={}", command.getRoomId());
    roomComponent.openNextSlotFor(command.getRoomId());
  }
}
