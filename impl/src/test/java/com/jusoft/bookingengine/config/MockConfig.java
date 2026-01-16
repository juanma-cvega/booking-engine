package com.jusoft.bookingengine.config;

import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@Configuration
public class MockConfig {

  @Bean
  @Primary
  public Clock clock() {
    return new ClockStub(Clock.systemUTC());
  }

  @Bean
  @Primary
  public MessagePublisher messagePublisher() {
    return Mockito.mock(MessagePublisher.class);
  }
}
