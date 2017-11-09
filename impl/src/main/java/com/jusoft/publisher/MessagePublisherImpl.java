package com.jusoft.publisher;

import com.jusoft.component.shared.Message;
import com.jusoft.component.shared.MessagePublisher;
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
