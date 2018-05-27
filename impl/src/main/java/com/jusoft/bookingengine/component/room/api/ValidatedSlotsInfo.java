package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class ValidatedSlotsInfo {

  private final int slotDurationTimeInMinutes;
  private final List<SystemLocalTime> invalidSlots;
}
