package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class BuildingComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public BuildingComponent buildingComponent() {
    return new BuildingComponentImpl(buildingFactory(), repository(), messagePublisher);
  }

  private BuildingFactory buildingFactory() {
    return new BuildingFactory(idGenerator());
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }

  private BuildingRepository repository() {
    return new BuildingRepositoryInMemory(new ConcurrentHashMap<>());
  }
}
