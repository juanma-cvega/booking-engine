package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.member.api.MemberComponent;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionStrategyRegistrar;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class UseCaseConfig {

  @Autowired
  private AuctionComponent auctionComponent;
  @Autowired
  private RoomComponent roomComponent;
  @Autowired
  private SlotComponent slotComponent;
  @Autowired
  private BookingComponent bookingComponent;
  @Autowired
  private BuildingComponent buildingComponent;
  @Autowired
  private ClubComponent clubComponent;
  @Autowired
  private MemberComponent memberComponent;
  @Autowired
  private SchedulerComponent schedulerComponent;
  @Autowired
  private AuctionStrategyRegistrar auctionStrategyRegistrar;
  @Autowired
  private SlotCreationStrategyRegistrar slotCreationStrategyRegistrar;
  @Autowired
  private Clock clock;
  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public AddBidderToAuctionUseCase addBidderToAuctionUseCase() {
    return new AddBidderToAuctionUseCase(auctionComponent);
  }

  @Bean
  public CancelBookingUseCase cancelBookingUseCase() {
    return new CancelBookingUseCase(bookingComponent, slotComponent);
  }

  @Bean
  public CreateBookingUseCase createBookingUseCase() {
    return new CreateBookingUseCase(bookingComponent, slotComponent, auctionComponent, memberComponent);
  }

  @Bean
  public CreateRoomUseCase createRoomUseCase() {
    return new CreateRoomUseCase(roomComponent, buildingComponent);
  }

  @Bean
  public FinishAuctionUseCase finishAuctionUseCase() {
    return new FinishAuctionUseCase(roomComponent, auctionComponent, auctionStrategyRegistrar);
  }

  @Bean
  public OpenNextSlotUseCase openNextSlotUseCase() {
    return new OpenNextSlotUseCase(roomComponent, slotComponent);
  }

  @Bean
  public ScheduleNextSlotUseCase scheduleNextSlotUseCase() {
    return new ScheduleNextSlotUseCase(roomComponent, schedulerComponent, slotCreationStrategyRegistrar);
  }

  @Bean
  public StartAuctionUseCase startAuctionUseCase() {
    return new StartAuctionUseCase(auctionComponent, roomComponent, schedulerComponent);
  }

  @Bean
  public CreateBuildingUseCase createBuildingUseCase() {
    return new CreateBuildingUseCase(clubComponent, buildingComponent);
  }

  @Bean
  public CreateClubUseCase createClubUseCase() {
    return new CreateClubUseCase(clubComponent);
  }

  @Bean
  public AcceptJoinRequestUseCase acceptJoinRequestUseCase() {
    return new AcceptJoinRequestUseCase(clubComponent);
  }

  @Bean
  public DenyJoinRequestUseCase denyJoinRequestUseCase() {
    return new DenyJoinRequestUseCase(clubComponent);
  }

  @Bean
  public FindClubByNameUseCase findClubByNameUseCase() {
    return new FindClubByNameUseCase(clubComponent);
  }

  @Bean
  public CreateJoinRequestUseCase createJoinRequestUseCase() {
    return new CreateJoinRequestUseCase(clubComponent);
  }

  @Bean
  public CreateMemberUseCase createMemberUseCase() {
    return new CreateMemberUseCase(memberComponent);
  }

  @Bean
  public FindJoinRequestsUseCase findJoinRequestsUseCase() {
    return new FindJoinRequestsUseCase(clubComponent);
  }
}
