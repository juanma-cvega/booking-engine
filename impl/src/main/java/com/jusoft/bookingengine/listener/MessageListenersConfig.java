package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.room.api.RoomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenersConfig {

  @Autowired
  private RoomComponent roomComponent;

  @Bean
  public OpenNextSlotCommandListener createSlotCommandListener() {
    return new OpenNextSlotCommandListener(roomComponent);
  }

  @Bean
  public SlotCreatedEventListener slotCreatedEventListener() {
    return new SlotCreatedEventListener(roomComponent);
  }

  @Bean
  public RoomCreatedEventListener roomCreatedEventListener() {
    return new RoomCreatedEventListener(roomComponent);
  }
}
