package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class InstructorTimetablesManagerConfig {

  @Bean
  public InstructorTimetablesManagerComponent instructorTimetablesManagerComponent() {
    return new InstructorTimetablesManagerComponentImpl(repository());
  }

  private InstructorTimetablesManagerRepository repository() {
    return new InstructorTimetablesManagerRepositoryInMemory(new ConcurrentHashMap<>());
  }
}
