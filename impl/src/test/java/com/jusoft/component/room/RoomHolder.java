package com.jusoft.component.room;

import com.jusoft.component.room.api.CreateRoomCommand;
import com.jusoft.component.timer.OpenTime;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static com.jusoft.component.room.RoomFixtures.ACTIVE;
import static com.jusoft.component.room.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.component.room.RoomFixtures.MAX_SLOTS;
import static com.jusoft.component.room.RoomFixtures.OPEN_TIMES;
import static com.jusoft.component.room.RoomFixtures.SLOT_DURATION_IN_MINUTES;

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

    public CreateRoomCommand build() {
      return new CreateRoomCommand(
        maxSlots == null ? MAX_SLOTS : maxSlots,
        slotDurationInMinutes == null ? SLOT_DURATION_IN_MINUTES : slotDurationInMinutes,
        openTimes.isEmpty() ? OPEN_TIMES : openTimes,
        availableDays.isEmpty() ? AVAILABLE_DAYS : availableDays,
        active == null ? ACTIVE : active);
    }

  }
}
