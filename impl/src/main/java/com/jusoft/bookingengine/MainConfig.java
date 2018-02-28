package com.jusoft.bookingengine;

import com.jusoft.bookingengine.component.auction.AuctionManagerComponentConfig;
import com.jusoft.bookingengine.component.authorization.AuthorizationManagerComponentConfig;
import com.jusoft.bookingengine.component.booking.BookingManagerComponentConfig;
import com.jusoft.bookingengine.component.building.BuildingManagerComponentConfig;
import com.jusoft.bookingengine.component.club.ClubManagerComponentConfig;
import com.jusoft.bookingengine.component.member.MemberManagerComponentConfig;
import com.jusoft.bookingengine.component.room.RoomManagerComponentConfig;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponentConfig;
import com.jusoft.bookingengine.component.slot.SlotManagerComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import com.jusoft.bookingengine.strategy.auctionwinner.AuctionWinnerStrategyConfig;
import com.jusoft.bookingengine.strategy.slotcreation.SlotCreationConfig;
import com.jusoft.bookingengine.usecase.UseCaseConfig;
import com.jusoft.bookingengine.usecase.authorization.AuthorizationUseCaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  TimerConfig.class,
  SchedulerComponentConfig.class,
  BookingManagerComponentConfig.class,
  SlotManagerComponentConfig.class,
  RoomManagerComponentConfig.class,
  AuctionManagerComponentConfig.class,
  BuildingManagerComponentConfig.class,
  ClubManagerComponentConfig.class,
  MemberManagerComponentConfig.class,
  AuctionWinnerStrategyConfig.class,
  SlotCreationConfig.class,
  AuthorizationManagerComponentConfig.class,
  UseCaseConfig.class,
  AuthorizationUseCaseConfig.class})
public class MainConfig {
}
