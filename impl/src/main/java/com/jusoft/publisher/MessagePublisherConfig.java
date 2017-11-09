package com.jusoft.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagePublisherConfig {

  @Autowired
  private ApplicationEventPublisher publisher;

  @Bean
  public MessagePublisherImpl messagePublisher() {
    return new MessagePublisherImpl(publisher);
  }
}
