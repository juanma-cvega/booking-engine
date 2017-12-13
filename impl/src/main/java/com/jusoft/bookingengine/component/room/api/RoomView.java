package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.auction.api.strategy.AuctionConfigInfo;
import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomView {

  private final long id;
  private final int maxSlots;
  private final int slotDurationInMinutes;
  private final List<OpenTime> openTimesPerDay;
  private final List<DayOfWeek> availableDays;
  private final boolean active;
  private final AuctionConfigInfo auctionConfigInfo;

  public RoomView(long id,
                  int maxSlots,
                  int slotDurationInMinutes,
                  List<OpenTime> openTimesPerDay,
                  List<DayOfWeek> availableDays,
                  boolean active,
                  AuctionConfigInfo auctionConfigInfo) {
    this.id = id;
    this.maxSlots = maxSlots;
    this.slotDurationInMinutes = slotDurationInMinutes;
    this.openTimesPerDay = new ArrayList<>(openTimesPerDay);
    this.availableDays = new ArrayList<>(availableDays);
    this.active = active;
    this.auctionConfigInfo = auctionConfigInfo;
  }

  public List<OpenTime> getOpenTimesPerDay() {
    return new ArrayList<>(openTimesPerDay);
  }

  public List<DayOfWeek> getAvailableDays() {
    return new ArrayList<>(availableDays);
  }
}
