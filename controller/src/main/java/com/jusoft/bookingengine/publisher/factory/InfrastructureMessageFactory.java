package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import com.jusoft.bookingengine.publisher.Message;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class InfrastructureMessageFactory {

  private final Map<Class<? extends Message>, MessageFactory> factories;

  @SuppressWarnings("unchecked")
  public InfrastructureMessage createFrom(Message message) {
    try {
      return factories.get(message.getClass()).createFrom(message);
    } catch (NullPointerException npe) {
      throw new IllegalArgumentException(String.format("Factory not found for message of type %s", message.getClass().getSimpleName()));
    }
  }
}
