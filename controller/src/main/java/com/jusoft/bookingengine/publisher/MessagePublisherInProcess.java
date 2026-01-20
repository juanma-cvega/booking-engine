package com.jusoft.bookingengine.publisher;

import com.jusoft.bookingengine.publisher.factory.InfrastructureMessageFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MessagePublisherInProcess implements MessagePublisher {

    private final ApplicationEventPublisher publisher;
    private final InfrastructureMessageFactory messageFactory;

    @Override
    public void publish(Message message) {
        publisher.publishEvent(messageFactory.createFrom(message));
    }
}
