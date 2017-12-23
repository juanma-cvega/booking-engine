package com.jusoft.bookingengine.config;

import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class MockConfig {

  @Bean
  public Clock clock() {
    return new ClockStub(Clock.systemUTC());
  }

  @Bean
  public MessagePublisher messagePublisher() {
    return Mockito.mock(MessagePublisher.class);
  }
}
