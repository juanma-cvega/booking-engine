package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class RoomManagerComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;
  @Autowired
  private Clock clock;

  @Bean
  public RoomManagerComponent roomComponent() {
    return new RoomManagerComponentImpl(roomRepository(), roomFactory(), roomEventFactory(), messagePublisher, clock);
  }

  private RoomFactory roomFactory() {
    return new RoomFactory(idGenerator());
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }

  private RoomMessageFactory roomEventFactory() {
    return new RoomMessageFactory();
  }

  private RoomRepository roomRepository() {
    return new RoomRepositoryInMemory(new ConcurrentHashMap<>());
  }
}
