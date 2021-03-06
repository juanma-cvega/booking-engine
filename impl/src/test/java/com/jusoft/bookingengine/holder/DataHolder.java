package com.jusoft.bookingengine.holder;

import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.CreateClassCommand;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.member.api.MemberView;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservedSlotsOfDay;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import com.jusoft.bookingengine.usecase.slotlifecycle.CreateSlotLifeCycleManagerCommand;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.jusoft.bookingengine.component.timer.SystemLocalTime.ofToday;
import static com.jusoft.bookingengine.fixture.ClassFixtures.CLASS_DESCRIPTION;
import static com.jusoft.bookingengine.fixture.ClassFixtures.CLASS_TYPE;
import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;
import static java.util.stream.Collectors.toList;

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
  public static List<BuildingView> buildingsCreated = new ArrayList<>();
  public static JoinRequest joinRequestCreated;
  public static Long clubAdmin;
  public static Set<JoinRequest> joinRequestsCreated = new HashSet<>();
  public static MemberView memberCreated;
  public static SlotLifeCycleManagerView slotLifeCycleManager;
  public static ClassView classCreated;
  public static List<ClassView> classesCreated;

  public static RuntimeException exceptionThrown;

  public static CreateRoomCommandBuilder roomBuilder;
  public static CreateSlotLifeCycleManagerBuilder createSlotLifeCycleManagerBuilder;
  public static SlotsTimetableBuilder slotsTimetableBuilder;

  public static void createRoomBuilder() {
    roomBuilder = new CreateRoomCommandBuilder();
  }

  public static void createSlotLifeCycleManagerBuilder(long roomId) {
    createSlotLifeCycleManagerBuilder = new CreateSlotLifeCycleManagerBuilder(roomId);
  }

  public static void createSlotsTimetableBuilder() {
    slotsTimetableBuilder = new SlotsTimetableBuilder();
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
    buildingsCreated = new ArrayList<>();
    joinRequestCreated = null;
    clubAdmin = null;
    joinRequestsCreated = new HashSet<>();
    memberCreated = null;
    slotLifeCycleManager = null;
    classCreated = null;
    classesCreated = new ArrayList<>();

    roomBuilder = null;
    createSlotLifeCycleManagerBuilder = null;
    slotsTimetableBuilder = null;

    exceptionThrown = null;
  }

  public static class CreateRoomCommandBuilder {

    public SlotCreationConfigInfo slotCreationConfigInfo;
    public Integer slotDurationInMinutes;
    public List<OpenTime> openTimes = new ArrayList<>();
    public List<DayOfWeek> availableDays = new ArrayList<>();

    public CreateRoomCommand build(long buildingId) {
      return CreateRoomCommand.of(
        buildingId,
        slotCreationConfigInfo == null ? MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO : slotCreationConfigInfo,
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays
      );
    }
  }

  @RequiredArgsConstructor
  public static class CreateSlotLifeCycleManagerBuilder {

    public final long roomId;
    public final SlotsTimetableBuilder slotsTimetableBuilder = new SlotsTimetableBuilder();

    public CreateSlotLifeCycleManagerCommand build() {
      return CreateSlotLifeCycleManagerCommand.of(
        roomId, slotsTimetableBuilder.build());
    }
  }

  public static class SlotsTimetableBuilder {
    public Integer slotDurationInMinutes;
    public List<OpenTime> openTimes = new ArrayList<>();
    public List<DayOfWeek> availableDays = new ArrayList<>();

    public SlotsTimetable build() {
      return SlotsTimetable.of(
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays
      );
    }
  }

  @AllArgsConstructor
  public static class ReservedSlotsOfDayHolder {
    private final DayOfWeek dayOfWeek;
    private final String slotsStartTime;
    private final String zoneId;

    public ReservedSlotsOfDay build(Clock clock) {
      return ReservedSlotsOfDay.of(dayOfWeek,
        Stream.of(slotsStartTime.split(","))
          .map(localTime -> ofToday(localTime, ZoneId.of(zoneId), clock))
          .collect(toList()));
    }
  }

  public static class CreateClassCommandBuilder {
    public Long buildingId;
    public String description;
    public String type;
    public List<Long> instructors = new ArrayList<>();
    public List<Long> roomRegistered = new ArrayList<>();

    public CreateClassCommand build(long buildingId, List<Long> instructors) {
      return CreateClassCommand.of(buildingId,
        description == null ? CLASS_DESCRIPTION : description,
        type == null ? CLASS_TYPE : type,
        instructors
      );
    }
  }
}
