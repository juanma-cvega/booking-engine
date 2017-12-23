//package com.jusoft.bookingengine.component.mock;
//
//import com.jusoft.bookingengine.publisher.Message;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//public class MessagesSink {
//
//  private final Map<Class<? extends Message>, List<Message>> messages;
//
//  public MessagesSink(Map<Class<? extends Message>, List<Message>> messages) {
//    this.messages = messages;
//  }
//
//  public <T extends Message> Optional<List<T>> getMessages(Class<T> clazz) {
//    return Optional.ofNullable((List<T>) this.messages.get(clazz));
//  }
//
//}
