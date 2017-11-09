package com.jusoft.component.mock;

import com.jusoft.component.shared.Message;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MessagesSink {

  private final List<Message> messages;

  public List<Message> getMessages() {
    return messages;
  }

  public List<Message> pollMessages() {
    List<Message> messagesRemoved = new ArrayList<>(messages);
    messages.clear();
    return messagesRemoved;
  }
}
