package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class BookingComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;

  @Autowired
  private Clock clock;

  @Bean
  public BookingComponent bookingComponent() {
    return new BookingComponentImpl(bookingRepository(), bookingFactory(), messagePublisher);
  }

  private BookingFactory bookingFactory() {
    return new BookingFactory(idGenerator(), clock);
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }

  private BookingRepository bookingRepository() {
    return new BookingRepositoryInMemory(new ConcurrentHashMap<>());
  }

}