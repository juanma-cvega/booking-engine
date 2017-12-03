package com.jusoft.bookingengine.component.mock;

import com.jusoft.bookingengine.component.shared.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class MessagesSink {

  private final Map<Class<? extends Message>, List<Message>> messages;

  public MessagesSink(Map<Class<? extends Message>, List<Message>> messages) {
    this.messages = messages;
  }

  public <T extends Message> List<T> getMessages(Class<T> clazz) {
    return (List<T>) this.messages.get(clazz);
  }

}
