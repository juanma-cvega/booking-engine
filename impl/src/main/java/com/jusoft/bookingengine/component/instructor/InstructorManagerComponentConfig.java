package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class InstructorManagerComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public InstructorManagerComponent instructorManagerComponent() {
    return new InstructorManagerComponentImpl(repository(), factory(), messagePublisher);
  }

  private InstructorManagerRepository repository() {
    return new InstructorManagerRepositoryInMemory(new ConcurrentHashMap<>());
  }

  private InstructorFactory factory() {
    AtomicLong idSupplier = new AtomicLong();
    return new InstructorFactory(idSupplier::getAndIncrement);
  }
}
