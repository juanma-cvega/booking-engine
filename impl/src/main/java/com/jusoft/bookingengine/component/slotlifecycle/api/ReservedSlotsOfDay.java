package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.jusoft.bookingengine.component.timer.SystemLocalTime.ofToday;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ReservedSlotsOfDay {

  private final DayOfWeek dayOfWeek;
  private final List<SystemLocalTime> slotsStartTime;

  public static ReservedSlotsOfDay of(DayOfWeek dayOfWeek, List<SystemLocalTime> slotsStartTime) {
    Objects.requireNonNull(dayOfWeek);
    Validate.notEmpty(slotsStartTime);
    return new ReservedSlotsOfDay(dayOfWeek, slotsStartTime);
  }

  public boolean contains(ZonedDateTime startDateTime, Clock clock) {
    return isSameDayOfWeek(startDateTime) && isTimeContainedInReservedStartTimes(startDateTime, clock);
  }

  private boolean isTimeContainedInReservedStartTimes(ZonedDateTime dateTime, Clock clock) {
    return slotsStartTime.stream()
      .anyMatch(reservedStartTime -> reservedStartTime.equals(ofToday(dateTime.toLocalTime(), dateTime.getZone(), clock)));
  }

  private boolean isSameDayOfWeek(ZonedDateTime startDateTime) {
    return dayOfWeek == startDateTime.getDayOfWeek();
  }

  public boolean intersectsWith(ReservedSlotsOfDay reservedSlotsOfDay) {
    return reservedSlotsOfDay.dayOfWeek == this.dayOfWeek && isReservedSlotsOfDayContainedIn(reservedSlotsOfDay);
  }

  private boolean isReservedSlotsOfDayContainedIn(ReservedSlotsOfDay reservedSlotsOfDay) {
    List<SystemLocalTime> slots = new ArrayList<>(reservedSlotsOfDay.slotsStartTime);
    slots.removeAll(slotsStartTime);
    return slots.size() != reservedSlotsOfDay.getSlotsStartTime().size();
  }

  public List<SystemLocalTime> getSlotsStartTime() {
    return new ArrayList<>(slotsStartTime);
  }
}
