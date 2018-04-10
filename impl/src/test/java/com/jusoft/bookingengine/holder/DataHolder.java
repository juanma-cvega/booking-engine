package com.jusoft.bookingengine.holder;

import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.member.api.MemberView;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservedSlotsOfDay;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import com.jusoft.bookingengine.usecase.slotlifecycle.CreateSlotLifeCycleManagerCommand;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.LESS_BOOKINGS_WITHIN_PERIOD_CONFIG;
import static com.jusoft.bookingengine.fixture.RoomFixtures.MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;

@UtilityClass
public class DataHolder {

  public static RoomView roomCreated;
  public static SlotView slotCreated;
  public static BookingView bookingCreated;
  public static List<BookingView> bookingsCreated = new ArrayList<>();
  public static List<BookingView> bookingsFetched = new ArrayList<>();
  public static AuctionView auctionCreated;
  public static ClubView clubCreated;
  public static BuildingView buildingCreated;
  public static JoinRequest joinRequestCreated;
  public static Long clubAdmin;
  public static Set<JoinRequest> joinRequestsCreated = new HashSet<>();
  public static MemberView memberCreated;

  public static RuntimeException exceptionThrown;
  public static CreateRoomCommandBuilder roomBuilder;
  public static CreateSlotLifeCycleManagerBuilder createSlotLifeCycleManagerBuilder;
  public static SlotValidationInfoBuilder slotValidationInfoBuilder;

  public static void createRoomBuilder() {
    roomBuilder = new CreateRoomCommandBuilder();
  }

  public static void createSlotLifeCycleManagerBuilder(long roomId) {
    createSlotLifeCycleManagerBuilder = new CreateSlotLifeCycleManagerBuilder(roomId);
  }

  public static void createSlotValidationInfoBuilder() {
    slotValidationInfoBuilder = new SlotValidationInfoBuilder();
  }

  public static void clear() {
    joinRequestsCreated = new HashSet<>();
    roomCreated = null;
    slotCreated = null;
    bookingCreated = null;
    bookingsCreated = new ArrayList<>();
    bookingsFetched = new ArrayList<>();
    auctionCreated = null;
    clubCreated = null;
    buildingCreated = null;
    joinRequestCreated = null;
    clubAdmin = null;
    joinRequestsCreated = new HashSet<>();
    memberCreated = null;

    roomBuilder = null;
    createSlotLifeCycleManagerBuilder = null;
    slotValidationInfoBuilder = null;

    exceptionThrown = null;
  }

  public static class CreateRoomCommandBuilder {

    public SlotCreationConfigInfo slotCreationConfigInfo;
    public Integer slotDurationInMinutes;
    public List<OpenTime> openTimes = new ArrayList<>();
    public List<DayOfWeek> availableDays = new ArrayList<>();
    public AuctionConfigInfo auctionConfigInfo;

    public CreateRoomCommand build(long buildingId) {
      return CreateRoomCommand.of(
        buildingId,
        slotCreationConfigInfo == null ? MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO : slotCreationConfigInfo,
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays,
        auctionConfigInfo == null ? LESS_BOOKINGS_WITHIN_PERIOD_CONFIG : auctionConfigInfo
      );
    }
  }

  @RequiredArgsConstructor
  public static class CreateSlotLifeCycleManagerBuilder {

    public final long roomId;
    public final SlotValidationInfoBuilder slotValidationInfoBuilder = new SlotValidationInfoBuilder();

    public CreateSlotLifeCycleManagerCommand build() {
      return CreateSlotLifeCycleManagerCommand.of(
        roomId, slotValidationInfoBuilder.build());
    }
  }

  public static class SlotValidationInfoBuilder {
    public Integer slotDurationInMinutes;
    public List<OpenTime> openTimes = new ArrayList<>();
    public List<DayOfWeek> availableDays = new ArrayList<>();

    public SlotValidationInfo build() {
      return SlotValidationInfo.of(
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays
      );
    }
  }

  @AllArgsConstructor
  public static class DayReservedSlotsHolder {
    private final DayOfWeek dayOfWeek;
    private final String slotsStartTime;
    private final String zoneId;

    public ReservedSlotsOfDay build(Clock clock) {
      return ReservedSlotsOfDay.of(dayOfWeek,
        Stream.of(slotsStartTime.split(",")).map(LocalTime::parse).collect(Collectors.toList()),
        LocalDate.now(clock),
        ZoneId.of(zoneId),
        clock);
    }
  }
}
