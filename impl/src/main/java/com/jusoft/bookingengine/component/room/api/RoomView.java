package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomView {

  private final long id;
  private final long buildingId;
  @NonNull
  private final SlotCreationConfigInfo slotCreationConfigInfo;
  private final int slotDurationInMinutes;
  @NonNull
  private final List<OpenTime> openTimesPerDay;
  @NonNull
  private final List<DayOfWeek> availableDays;
  private final boolean active;
  @NonNull
  private final AuctionConfigInfo auctionConfigInfo;

  private RoomView(long id,
                   long buildingId,
                   SlotCreationConfigInfo slotCreationConfigInfo,
                   int slotDurationInMinutes,
                   List<OpenTime> openTimesPerDay,
                   List<DayOfWeek> availableDays,
                   boolean active,
                   AuctionConfigInfo auctionConfigInfo) {
    this.id = id;
    this.buildingId = buildingId;
    this.slotCreationConfigInfo = slotCreationConfigInfo;
    this.slotDurationInMinutes = slotDurationInMinutes;
    this.openTimesPerDay = new ArrayList<>(openTimesPerDay);
    this.availableDays = new ArrayList<>(availableDays);
    this.active = active;
    this.auctionConfigInfo = auctionConfigInfo;
  }

  public static RoomView of(long id,
                            long buildingId,
                            SlotCreationConfigInfo slotCreationConfigInfo,
                            int slotDurationInMinutes,
                            List<OpenTime> openTimesPerDay,
                            List<DayOfWeek> availableDays,
                            boolean active,
                            AuctionConfigInfo auctionConfigInfo) {
    return new RoomView(id, buildingId, slotCreationConfigInfo, slotDurationInMinutes, openTimesPerDay, availableDays, active, auctionConfigInfo);
  }

  public List<OpenTime> getOpenTimesPerDay() {
    return new ArrayList<>(openTimesPerDay);
  }

  public List<DayOfWeek> getAvailableDays() {
    return new ArrayList<>(availableDays);
  }
}
