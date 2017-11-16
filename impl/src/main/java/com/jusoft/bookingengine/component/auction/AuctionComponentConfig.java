package com.jusoft.bookingengine.component.auction;

import com.google.common.collect.ImmutableMap;
import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerStrategyType;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static com.jusoft.bookingengine.component.auction.api.AuctionWinnerStrategyType.LESS_BOOKINGS_WITHIN_PERIOD;

@Configuration
public class AuctionComponentConfig {

  @Autowired
  private MessagePublisher messagePublisher;
  @Autowired
  private RoomComponent roomComponent;
  @Autowired
  private BookingComponent bookingComponent;
  @Autowired
  private Clock clock;

  @Bean
  public AuctionComponent auctionComponent() {
    return new AuctionComponentImpl(auctionRepository(), auctionStrategyRegistrar(), auctionFactory(), roomComponent, messagePublisher);
  }

  private AuctionRepository auctionRepository() {
    return new AuctionRepositoryInMemory();
  }

  private AuctionStrategyRegistrar auctionStrategyRegistrar() {
    return new AuctionStrategyRegistrar(strategies());
  }

  private Map<AuctionWinnerStrategyType, AuctionWinnerStrategy> strategies() {
    return ImmutableMap.of(LESS_BOOKINGS_WITHIN_PERIOD, lessBookingsWithinPeriodStrategy());
  }

  private LessBookingsWithinPeriodStrategy lessBookingsWithinPeriodStrategy() {
    return new LessBookingsWithinPeriodStrategy(bookingComponent, clock);
  }

  private AuctionFactory auctionFactory() {
    return new AuctionFactory(idGenerator(), clock);
  }

  private Supplier<Long> idGenerator() {
    AtomicLong idGenerator = new AtomicLong(1);
    return idGenerator::getAndIncrement;
  }
}

