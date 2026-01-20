package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.Event;
import java.time.DayOfWeek;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class RoomCreatedEvent implements Event {

    private final long roomId;
    private final int slotDurationInMinutes;

    @NonNull private final List<OpenTime> openTimesPerDay;

    @NonNull private final List<DayOfWeek> availableDays;
}
