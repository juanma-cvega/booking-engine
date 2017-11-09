package com.jusoft.component.slot;

import com.jusoft.component.shared.MessagePublisher;
import com.jusoft.component.slot.api.SlotComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class SlotComponentConfig {

  @Autowired
  private Clock clock;

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public SlotComponent slotComponent() {
    return new SlotComponentImpl(slotRepository(), slotFactory(), slotEventFactory(), messagePublisher);
  }

  private SlotEventFactory slotEventFactory() {
    return new SlotEventFactory();
  }

  private SlotRepository slotRepository() {
    return new SlotRepositoryInMemory(new ConcurrentHashMap<>(), clock);
  }

  private SlotFactory slotFactory() {
    return new SlotFactory(idGenerator());
  }

  private Supplier<Long> idGenerator() {
    AtomicLong generator = new AtomicLong(1);
    return generator::getAndIncrement;
  }
}
