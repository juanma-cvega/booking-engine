package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionStrategyRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuctionUseCaseConfig {

  @Autowired
  private AuctionManagerComponent auctionManagerComponent;

  @Autowired
  private AuctionStrategyRegistrar auctionStrategyRegistrar;

  @Autowired
  private SchedulerComponent schedulerComponent;

  @Bean
  public AddBidderToAuctionUseCase addBidderToAuctionUseCase() {
    return new AddBidderToAuctionUseCase(auctionManagerComponent);
  }

  @Bean
  public FinishAuctionUseCase finishAuctionUseCase() {
    return new FinishAuctionUseCase(auctionManagerComponent, auctionStrategyRegistrar);
  }

  @Bean
  public ScheduleFinishAuctionUseCase scheduleFinishAuctionUseCase() {
    return new ScheduleFinishAuctionUseCase(schedulerComponent);
  }

  @Bean
  public StartAuctionUseCase startAuctionUseCase() {
    return new StartAuctionUseCase(auctionManagerComponent);
  }

}
