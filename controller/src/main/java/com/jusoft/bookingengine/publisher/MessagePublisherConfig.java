package com.jusoft.bookingengine.publisher;

import com.jusoft.bookingengine.publisher.factory.InfrastructureMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagePublisherConfig {

    @Autowired private ApplicationEventPublisher publisher;

    @Autowired private InfrastructureMessageFactory infrastructureMessageFactory;

    @Bean
    public MessagePublisher messagePublisher() {
        return new MessagePublisherInProcess(publisher, infrastructureMessageFactory);
    }
}
