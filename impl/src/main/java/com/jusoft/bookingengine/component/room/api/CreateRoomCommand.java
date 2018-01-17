package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.Command;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateRoomCommand implements Command {
  private final long buildingId;
  @NonNull
  private final SlotCreationConfigInfo slotCreationConfigInfo;
  private final int slotDurationInMinutes;
  @NonNull
  private final List<OpenTime> openTimePerDay;
  @NonNull
  private final List<DayOfWeek> availableDays;
  private final boolean active;
  @NonNull
  private final AuctionConfigInfo auctionConfigInfo;


  public List<OpenTime> getOpenTimePerDay() {
    return new ArrayList<>(openTimePerDay);
  }

  public List<DayOfWeek> getAvailableDays() {
    return new ArrayList<>(availableDays);
  }
}
