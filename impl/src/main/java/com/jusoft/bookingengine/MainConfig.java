package com.jusoft.bookingengine;

import com.jusoft.bookingengine.component.auction.AuctionComponentConfig;
import com.jusoft.bookingengine.component.booking.BookingComponentConfig;
import com.jusoft.bookingengine.component.building.BuildingComponentConfig;
import com.jusoft.bookingengine.component.club.ClubComponentConfig;
import com.jusoft.bookingengine.component.room.RoomComponentConfig;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponentConfig;
import com.jusoft.bookingengine.component.slot.SlotComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import com.jusoft.bookingengine.strategy.auctionwinner.AuctionWinnerStrategyConfig;
import com.jusoft.bookingengine.strategy.slotcreation.SlotCreationConfig;
import com.jusoft.bookingengine.usecase.UseCaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  TimerConfig.class,
  SchedulerComponentConfig.class,
  BookingComponentConfig.class,
  SlotComponentConfig.class,
  RoomComponentConfig.class,
  AuctionComponentConfig.class,
  BuildingComponentConfig.class,
  ClubComponentConfig.class,
  AuctionWinnerStrategyConfig.class,
  SlotCreationConfig.class,
  UseCaseConfig.class})
public class MainConfig {
}
