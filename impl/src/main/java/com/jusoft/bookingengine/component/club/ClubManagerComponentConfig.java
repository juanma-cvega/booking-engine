package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Configuration
public class ClubManagerComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public ClubManagerComponent clubComponent() {
    return new ClubManagerComponentImpl(buildingFactory(), joinRequestFactory(), repository(), messagePublisher);
  }

  private JoinRequestFactory joinRequestFactory() {
    return new JoinRequestFactory(idGeneratorForJoinRequests());
  }

  private Supplier<Long> idGeneratorForJoinRequests() {
    return inMemoryGeneratorSupplier();
  }

  private ClubFactory buildingFactory() {
    return new ClubFactory(idGeneratorForClubs());
  }

  private Supplier<Long> idGeneratorForClubs() {
    return inMemoryGeneratorSupplier();
  }

  private Supplier<Long> inMemoryGeneratorSupplier() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }

  private ClubRepository repository() {
    return new ClubRepositoryInMemory(new ConcurrentHashMap<>(), new ReentrantLock());
  }
}
