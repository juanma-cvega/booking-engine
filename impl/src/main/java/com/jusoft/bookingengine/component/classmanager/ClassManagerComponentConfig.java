package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ClassManagerComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public ClassManagerComponent classManagerComponent() {
    return new ClassManagerComponentImpl(repository(), factory(), messagePublisher);
  }

  private ClassManagerComponentRepository repository() {
    return new ClassManagerComponentRepositoryInMemory(new ConcurrentHashMap<>());
  }

  private ClassFactory factory() {
    AtomicLong idSupplier = new AtomicLong();
    return new ClassFactory(idSupplier::getAndIncrement);
  }
}
