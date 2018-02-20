package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Configuration
public class AuctionComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;
  @Autowired
  private BookingManagerComponent bookingManagerComponent;
  @Autowired
  private Clock clock;

  @Bean
  public AuctionManagerComponent auctionComponent() {
    return new AuctionManagerComponentImpl(auctionRepository(), auctionFactory(), messagePublisher, clock);
  }

  private AuctionRepository auctionRepository() {
    return new AuctionRepositoryInMemory(new ConcurrentHashMap<>(), new ReentrantLock());
  }

  private AuctionFactory auctionFactory() {
    return new AuctionFactory(idGenerator(), clock);
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }
}

