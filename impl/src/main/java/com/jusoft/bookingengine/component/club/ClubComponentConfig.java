package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class ClubComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public ClubComponent clubComponent() {
    return new ClubComponentImpl(buildingFactory(), repository(), messagePublisher);
  }

  private ClubFactory buildingFactory() {
    return new ClubFactory(idGenerator());
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }

  private ClubRepository repository() {
    return new ClubRepositoryInMemory(new ConcurrentHashMap<>());
  }
}
