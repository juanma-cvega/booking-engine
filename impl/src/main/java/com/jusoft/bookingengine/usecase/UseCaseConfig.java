package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
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
  private AuctionManagerComponent auctionManagerComponent;
  @Autowired
  private RoomManagerComponent roomManagerComponent;
  @Autowired
  private SlotManagerComponent slotManagerComponent;
  @Autowired
  private BookingManagerComponent bookingManagerComponent;
  @Autowired
  private BuildingManagerComponent buildingManagerComponent;
  @Autowired
  private ClubManagerComponent clubManagerComponent;
  @Autowired
  private MemberManagerComponent memberManagerComponent;
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
    return new AddBidderToAuctionUseCase(auctionManagerComponent);
  }

  @Bean
  public CancelBookingUseCase cancelBookingUseCase() {
    return new CancelBookingUseCase(bookingManagerComponent, slotManagerComponent);
  }

  @Bean
  public CreateBookingUseCase createBookingUseCase() {
    return new CreateBookingUseCase(bookingManagerComponent);
  }

  @Bean
  public CreateRoomUseCase createRoomUseCase() {
    return new CreateRoomUseCase(roomManagerComponent, buildingManagerComponent);
  }

  @Bean
  public FinishAuctionUseCase finishAuctionUseCase() {
    return new FinishAuctionUseCase(auctionManagerComponent, auctionStrategyRegistrar);
  }

  @Bean
  public CreateSlotUseCase openNextSlotUseCase() {
    return new CreateSlotUseCase(roomManagerComponent, slotManagerComponent);
  }

  @Bean
  public ScheduleNextSlotUseCase scheduleNextSlotUseCase() {
    return new ScheduleNextSlotUseCase(roomManagerComponent, schedulerComponent, slotCreationStrategyRegistrar);
  }

  @Bean
  public StartAuctionUseCase startAuctionUseCase() {
    return new StartAuctionUseCase(auctionManagerComponent);
  }

  @Bean
  public ScheduleFinishAuctionUseCase scheduleFinishAuctionUseCase() {
    return new ScheduleFinishAuctionUseCase(schedulerComponent);
  }

  @Bean
  public CreateBuildingUseCase createBuildingUseCase() {
    return new CreateBuildingUseCase(clubManagerComponent, buildingManagerComponent);
  }

  @Bean
  public CreateClubUseCase createClubUseCase() {
    return new CreateClubUseCase(clubManagerComponent);
  }

  @Bean
  public AcceptJoinRequestUseCase acceptJoinRequestUseCase() {
    return new AcceptJoinRequestUseCase(clubManagerComponent);
  }

  @Bean
  public DenyJoinRequestUseCase denyJoinRequestUseCase() {
    return new DenyJoinRequestUseCase(clubManagerComponent);
  }

  @Bean
  public FindClubByNameUseCase findClubByNameUseCase() {
    return new FindClubByNameUseCase(clubManagerComponent);
  }

  @Bean
  public CreateJoinRequestUseCase createJoinRequestUseCase() {
    return new CreateJoinRequestUseCase(clubManagerComponent);
  }

  @Bean
  public CreateMemberUseCase createMemberUseCase() {
    return new CreateMemberUseCase(memberManagerComponent);
  }

  @Bean
  public FindJoinRequestsUseCase findJoinRequestsUseCase() {
    return new FindJoinRequestsUseCase(clubManagerComponent);
  }

  @Bean
  public MakeSlotAvailableUseCase makeSlotAvailableUseCase() {
    return new MakeSlotAvailableUseCase(slotManagerComponent);
  }

  @Bean
  public ReserveSlotForAuctionWinnerUseCase reserveSlotForAuctionWinnerUseCase() {
    return new ReserveSlotForAuctionWinnerUseCase(slotManagerComponent);
  }

  @Bean
  public ReserveSlotUseCase reserveSlotUseCase() {
    return new ReserveSlotUseCase(slotManagerComponent, roomManagerComponent, buildingManagerComponent, memberManagerComponent);
  }

  @Bean
  public VerifyAuctionRequirementForSlotUseCase verifyAuctionRequirementForSlotUseCase() {
    return new VerifyAuctionRequirementForSlotUseCase(roomManagerComponent);
  }

  @Bean
  public GetBookingsUseCase getBookingsUseCase() {
    return new GetBookingsUseCase(bookingManagerComponent);
  }
}
