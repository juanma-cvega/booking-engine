package com.jusoft.bookingengine.component.slotlifecycle.api;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.Validate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(of = "classId")
public class ClassTimetable {

    private final long classId;
    private final List<ReservedSlotsOfDay> reservedSlotsOfDays;

    public static ClassTimetable of(long classId, List<ReservedSlotsOfDay> reservedSlotOfDays) {
        Validate.notEmpty(reservedSlotOfDays);
        return new ClassTimetable(classId, new ArrayList<>(reservedSlotOfDays));
    }

    public boolean contains(ZonedDateTime startTime, Clock clock) {
        return reservedSlotsOfDays.stream()
                .anyMatch(reservedSlotsOfDay -> reservedSlotsOfDay.contains(startTime, clock));
    }

    public boolean intersectsWith(List<ReservedSlotsOfDay> reservedSlotsOfDays) {
        return this.reservedSlotsOfDays.stream()
                .anyMatch(
                        reservedSlotsOfDay ->
                                reservedSlotsOfDays.stream()
                                        .anyMatch(
                                                configuredReservedSlotsOfDay ->
                                                        configuredReservedSlotsOfDay.intersectsWith(
                                                                reservedSlotsOfDay)));
    }

    public List<ReservedSlotsOfDay> getReservedSlotsOfDays() {
        return new ArrayList<>(reservedSlotsOfDays);
    }
}
