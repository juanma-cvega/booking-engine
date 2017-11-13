package com.jusoft.bookingengine.publisher;

import com.jusoft.bookingengine.component.shared.Message;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MessagePublisherImpl implements MessagePublisher {

  private final ApplicationEventPublisher publisher;

  @Override
  public void publish(Message message) {
    publisher.publishEvent(message);
  }
}
