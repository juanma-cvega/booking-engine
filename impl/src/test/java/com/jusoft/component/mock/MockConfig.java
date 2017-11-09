package com.jusoft.component.mock;

import com.jusoft.component.scheduler.SchedulerComponent;
import com.jusoft.component.shared.Message;
import com.jusoft.component.shared.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MockConfig {

  private final List<Message> messagesSent = new ArrayList<>();

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public Clock clock() {
    return new ClockStub(Clock.systemUTC());
  }

  @Bean
  public MessagesSink messagesSink() {
    return new MessagesSink(messagesSent);
  }

  @Bean
  public SchedulerComponent schedulerComponent() {
    return new SchedulerComponentMock(messagesSent, messagePublisher, clock());
  }
}
