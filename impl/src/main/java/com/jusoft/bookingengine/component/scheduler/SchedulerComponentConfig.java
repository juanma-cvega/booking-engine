package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class SchedulerComponentConfig {

  @Autowired
  private Clock clock;

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public SchedulerComponent schedulerComponent() {
    return new SchedulerComponentInMemory(clock, messagePublisher, executor(), tasks());
  }

  @Bean
  public List<ScheduledTask> tasks() {
    return new CopyOnWriteArrayList<>();
  }

  private Executor executor() {
    return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }
}
