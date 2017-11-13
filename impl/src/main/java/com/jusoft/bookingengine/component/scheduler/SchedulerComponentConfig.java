package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.component.shared.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SchedulerComponentConfig {

  @Autowired
  private Clock clock;

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public SchedulerComponent schedulerComponent() {
    return new SchedulerComponentImpl(clock, messagePublisher);
  }
}
