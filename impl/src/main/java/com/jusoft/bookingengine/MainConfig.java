package com.jusoft.bookingengine;

import com.jusoft.bookingengine.component.auction.AuctionManagerComponentConfig;
import com.jusoft.bookingengine.component.authorization.AuthorizationManagerComponentConfig;
import com.jusoft.bookingengine.component.booking.BookingManagerComponentConfig;
import com.jusoft.bookingengine.component.building.BuildingManagerComponentConfig;
import com.jusoft.bookingengine.component.classmanager.ClassManagerComponentConfig;
import com.jusoft.bookingengine.component.club.ClubManagerComponentConfig;
import com.jusoft.bookingengine.component.instructor.InstructorManagerComponentConfig;
import com.jusoft.bookingengine.component.member.MemberManagerComponentConfig;
import com.jusoft.bookingengine.component.room.RoomManagerComponentConfig;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponentConfig;
import com.jusoft.bookingengine.component.slot.SlotManagerComponentConfig;
import com.jusoft.bookingengine.component.slotlifecycle.SlotLifeCycleManagerComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import com.jusoft.bookingengine.strategy.auctionwinner.AuctionWinnerStrategyConfig;
import com.jusoft.bookingengine.strategy.slotcreation.SlotCreationStrategyConfig;
import com.jusoft.bookingengine.usecase.auction.AuctionUseCaseConfig;
import com.jusoft.bookingengine.usecase.authorization.AuthorizationUseCaseConfig;
import com.jusoft.bookingengine.usecase.booking.BookingUseCaseConfig;
import com.jusoft.bookingengine.usecase.building.BuildingUseCaseConfig;
import com.jusoft.bookingengine.usecase.classmanager.ClassManagerUseCaseConfig;
import com.jusoft.bookingengine.usecase.club.ClubUseCaseConfig;
import com.jusoft.bookingengine.usecase.instructor.InstructorUseCaseConfig;
import com.jusoft.bookingengine.usecase.member.MemberUseCaseConfig;
import com.jusoft.bookingengine.usecase.room.RoomUseCaseConfig;
import com.jusoft.bookingengine.usecase.slot.SlotUseCaseConfig;
import com.jusoft.bookingengine.usecase.slotlifecycle.SlotLifeCycleManagerUseCaseConfig;
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
  SlotLifeCycleManagerComponentConfig.class,
  InstructorManagerComponentConfig.class,
  AuctionWinnerStrategyConfig.class,
  SlotCreationStrategyConfig.class,
  AuthorizationManagerComponentConfig.class,
  ClassManagerComponentConfig.class,
  RoomUseCaseConfig.class,
  SlotUseCaseConfig.class,
  ClubUseCaseConfig.class,
  BuildingUseCaseConfig.class,
  MemberUseCaseConfig.class,
  BookingUseCaseConfig.class,
  AuctionUseCaseConfig.class,
  AuthorizationUseCaseConfig.class,
  SlotLifeCycleManagerUseCaseConfig.class,
  ClassManagerUseCaseConfig.class,
  InstructorUseCaseConfig.class})
public class MainConfig {
}
