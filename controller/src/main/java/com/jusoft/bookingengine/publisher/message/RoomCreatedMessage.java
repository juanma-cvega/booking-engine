package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class RoomCreatedMessage implements InfrastructureMessage {

  private final long roomId;
  private final int maxSlots;
  private final int slotDurationInMinutes;
  @NonNull
  private final List<OpenTime> openTimesPerDay;
  @NonNull
  private final List<DayOfWeek> availableDays;
  private final boolean active;
}
