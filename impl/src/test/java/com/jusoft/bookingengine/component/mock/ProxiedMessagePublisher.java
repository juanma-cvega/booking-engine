//package com.jusoft.bookingengine.component.mock;
//
//import com.jusoft.bookingengine.component.shared.Message;
//import com.jusoft.bookingengine.component.shared.MessagePublisher;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class ProxiedMessagePublisher implements MessagePublisher {
//
//  private final Map<Class<? extends Message>, List<Message>> messages;
//  private final MessagePublisher messagePublisher;
//
//  public ProxiedMessagePublisher(Map<Class<? extends Message>, List<Message>> messages, MessagePublisher messagePublisher) {
//    this.messages = messages;
//    this.messagePublisher = messagePublisher;
//  }
//
//  @Override
//  public void publish(Message message) {
//    List<Message> messages = this.messages.getOrDefault(message.getClass(), new CopyOnWriteArrayList<>());
//    messages.add(message);
//    this.messages.put(message.getClass(), messages);
//    messagePublisher.publish(message);
//  }
//}
