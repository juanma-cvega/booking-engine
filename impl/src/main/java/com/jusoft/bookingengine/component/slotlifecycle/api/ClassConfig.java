package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.Validate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(of = "classId")
public class ClassConfig {

  private final long classId;
  private final List<ReservedSlotsOfDay> reservedSlotsOfDays;

  public static ClassConfig of(long classId) {
    return of(classId, new ArrayList<>());
  }

  public static ClassConfig of(long classId, List<ReservedSlotsOfDay> reservedSlotOfDays) {
    Validate.notEmpty(reservedSlotOfDays);
    return new ClassConfig(classId, new ArrayList<>(reservedSlotOfDays));
  }

  public boolean contains(ZonedDateTime startTime) {
    return reservedSlotsOfDays.stream().anyMatch(dayReservedSlots -> dayReservedSlots.contains(startTime));
  }

  public boolean intersectsWith(ClassConfig classConfig) {
    return classConfig.getReservedSlotsOfDays().stream()
      .anyMatch(dayReservedSlots -> reservedSlotsOfDays.stream()
        .anyMatch(configuredDayReservedSlots -> configuredDayReservedSlots.intersectsWith(dayReservedSlots)));
  }

  public List<ReservedSlotsOfDay> getReservedSlotsOfDays() {
    return new ArrayList<>(reservedSlotsOfDays);
  }

}
