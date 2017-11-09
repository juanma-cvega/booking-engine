package com.jusoft.component.room;

import com.jusoft.component.room.api.CreateRoomCommand;
import com.jusoft.component.room.api.CreateRoomCommand.CreateRoomCommandBuilder;
import com.jusoft.component.timer.OpenTime;
import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@UtilityClass
public class RoomFixtures {

  public static final int MAX_SLOTS = 10;
  public static final int SLOT_DURATION_IN_MINUTES = 1;
  public static final LocalTime START_TIME_MORNING = LocalTime.of(8, 0);
  public static final LocalTime END_TIME_MORNING = LocalTime.of(12, 0);
  public static final OpenTime OPEN_TIME_MORNING = new OpenTime(START_TIME_MORNING, END_TIME_MORNING);
  public static final LocalTime START_TIME_AFTERNOON = LocalTime.of(14, 0);
  public static final LocalTime END_TIME_AFTERNOON = LocalTime.of(20, 0);
  public static final OpenTime OPEN_TIME_AFTERNOON = new OpenTime(START_TIME_AFTERNOON, END_TIME_AFTERNOON);
  public static final List<OpenTime> OPEN_TIMES = Arrays.asList(OPEN_TIME_MORNING, OPEN_TIME_AFTERNOON);
  public static final List<DayOfWeek> AVAILABLE_DAYS = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
  public static final boolean ACTIVE = true;

  public static final CreateRoomCommand CREATE_ROOM_COMMAND = createRoomCommand();

  private static CreateRoomCommand createRoomCommand() {
    return new CreateRoomCommand(MAX_SLOTS, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS, ACTIVE);
  }

  public static CreateRoomCommand createRoomCommand(Consumer<CreateRoomCommandBuilder> consumer) {
    CreateRoomCommandBuilder builder = CreateRoomCommand.builder()
      .active(ACTIVE)
      .availableDays(AVAILABLE_DAYS)
      .openTimePerDay(OPEN_TIMES)
      .maxSlots(MAX_SLOTS)
      .slotDurationInMinutes(SLOT_DURATION_IN_MINUTES);
    consumer.accept(builder);
    return builder.build();
  }
}
