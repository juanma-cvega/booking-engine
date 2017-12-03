package com.jusoft.bookingengine.component.auction;

import com.google.common.collect.ImmutableMap;
import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import com.jusoft.bookingengine.component.auction.api.strategy.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class AuctionComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;
  @Autowired
  private BookingComponent bookingComponent;
  @Autowired
  private Clock clock;

  @Bean
  public AuctionComponent auctionComponent() {
    return new AuctionComponentImpl(auctionRepository(), auctionStrategyRegistrar(), auctionFactory(), messagePublisher);
  }

  private AuctionRepository auctionRepository() {
    return new AuctionRepositoryInMemory(new ConcurrentHashMap<>());
  }

  private AuctionStrategyRegistrar auctionStrategyRegistrar() {
    return new AuctionStrategyRegistrar(factories());
  }

  private Map<Class<? extends AuctionConfigInfo>, AuctionWinnerStrategyFactory> factories() {
    return ImmutableMap.of(LessBookingsWithinPeriodConfigInfo.class, lessBookingsWithinPeriodStrategyFactory());
  }

  private LessBookingsWithinPeriodStrategyFactory lessBookingsWithinPeriodStrategyFactory() {
    return new LessBookingsWithinPeriodStrategyFactory(bookingComponent, clock);
  }

  private AuctionFactory auctionFactory() {
    return new AuctionFactory(idGenerator(), clock);
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }
}

