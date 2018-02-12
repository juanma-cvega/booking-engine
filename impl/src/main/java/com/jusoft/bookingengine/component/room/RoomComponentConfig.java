package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class RoomComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;
  @Autowired
  private Clock clock;

  @Bean
  public RoomComponent roomComponent() {
    return new RoomComponentImpl(roomRepository(), roomFactory(), roomEventFactory(), messagePublisher, clock);
  }

  private RoomFactory roomFactory() {
    return new RoomFactory(idGenerator());
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }

  private RoomMessageFactory roomEventFactory() {
    return new RoomMessageFactory(clock);
  }

  private RoomRepository roomRepository() {
    return new RoomRepositoryInMemory(new ConcurrentHashMap<>());
  }
}
