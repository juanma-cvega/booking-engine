package com.jusoft.bookingengine.holder;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.IS_ACTIVE;
import static com.jusoft.bookingengine.fixture.RoomFixtures.LESS_BOOKINGS_WITHIN_PERIOD_CONFIG;
import static com.jusoft.bookingengine.fixture.RoomFixtures.MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;

public class RoomHolder {

  public RoomView roomCreated;

  public CreateRoomCommandBuilder roomBuilder;

  public void createRoomBuilder() {
    roomBuilder = new CreateRoomCommandBuilder();
  }

  public static class CreateRoomCommandBuilder {

    public SlotCreationConfigInfo slotCreationConfigInfo;
    public Integer slotDurationInMinutes;
    public List<OpenTime> openTimes = new ArrayList<>();
    public List<DayOfWeek> availableDays = new ArrayList<>();
    public Boolean active;
    public AuctionConfigInfo auctionConfigInfo;

    public CreateRoomCommand build() {
      return new CreateRoomCommand(
        slotCreationConfigInfo == null ? MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO : slotCreationConfigInfo,
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays,
        active == null ? IS_ACTIVE : active,
        auctionConfigInfo == null ? LESS_BOOKINGS_WITHIN_PERIOD_CONFIG : auctionConfigInfo);
    }

  }
}
