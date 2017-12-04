package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.timer.OpenTime;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static com.jusoft.bookingengine.component.room.RoomFixtures.ACTIVE;
import static com.jusoft.bookingengine.component.room.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.component.room.RoomFixtures.LESS_BOOKINGS_WITHIN_PERIOD_CONFIG;
import static com.jusoft.bookingengine.component.room.RoomFixtures.MAX_SLOTS;
import static com.jusoft.bookingengine.component.room.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.component.room.RoomFixtures.SLOT_DURATION_IN_MINUTES;

public class RoomHolder {

  public Room roomCreated;
  public Room roomFetched;

  public CreateRoomCommandBuilder roomBuilder;

  public void createRoomBuilder() {
    roomBuilder = new CreateRoomCommandBuilder();
  }

  static class CreateRoomCommandBuilder {

    public Integer maxSlots;
    public Integer slotDurationInMinutes;
    public List<OpenTime> openTimes = new ArrayList<>();
    public List<DayOfWeek> availableDays = new ArrayList<>();
    public Boolean active;
    public AuctionConfigInfo auctionConfigInfo;

    public CreateRoomCommand build() {
      return new CreateRoomCommand(
        maxSlots == null ? MAX_SLOTS : maxSlots,
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays,
        active == null ? ACTIVE : active,
        auctionConfigInfo == null ? LESS_BOOKINGS_WITHIN_PERIOD_CONFIG : auctionConfigInfo);
    }

  }
}