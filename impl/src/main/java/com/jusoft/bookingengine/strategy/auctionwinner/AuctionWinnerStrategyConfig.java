package com.jusoft.bookingengine.strategy.auctionwinner;

import com.google.common.collect.ImmutableMap;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionStrategyRegistrar;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategyFactory;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Map;

@Configuration
public class AuctionWinnerStrategyConfig {

  @Autowired
  private BookingComponent bookingComponent;
  @Autowired
  private Clock clock;

  @Bean
  public AuctionStrategyRegistrar auctionStrategyRegistrar() {
    return new AuctionStrategyRegistrar(factories());
  }

  private Map<Class<? extends AuctionConfigInfo>, AuctionWinnerStrategyFactory> factories() {
    return ImmutableMap.<Class<? extends AuctionConfigInfo>, AuctionWinnerStrategyFactory>builder()
      .put(LessBookingsWithinPeriodConfigInfo.class, lessBookingsWithinPeriodStrategyFactory())
      .put(NoAuctionConfigInfo.class, noAuctionStrategyFactory())
      .build();
  }

  private LessBookingsWithinPeriodStrategyFactory lessBookingsWithinPeriodStrategyFactory() {
    return new LessBookingsWithinPeriodStrategyFactory(bookingComponent, clock);
  }

  private NoAuctionStrategyFactory noAuctionStrategyFactory() {
    return new NoAuctionStrategyFactory();
  }
}
