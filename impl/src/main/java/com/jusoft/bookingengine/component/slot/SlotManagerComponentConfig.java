package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Configuration
public class SlotManagerComponentConfig {

  @Autowired
  private Clock clock;

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public SlotManagerComponent slotComponent() {
    return new SlotManagerComponentImpl(slotRepository(), slotFactory(), slotEventFactory(), messagePublisher, clock);
  }

  private SlotEventFactory slotEventFactory() {
    return new SlotEventFactory();
  }

  private SlotRepository slotRepository() {
    return new SlotRepositoryInMemory(new ConcurrentHashMap<>(), new ReentrantLock(), clock);
  }

  private SlotFactory slotFactory() {
    return new SlotFactory(idGenerator(), clock);
  }

  private Supplier<Long> idGenerator() {
    AtomicLong generator = new AtomicLong(1);
    return generator::getAndIncrement;
  }
}
