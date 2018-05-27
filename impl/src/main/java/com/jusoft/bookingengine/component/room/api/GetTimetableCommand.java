package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
public class GetTimetableCommand {

  private final long roomId;
  @NonNull
  private final Map<DayOfWeek, List<SystemLocalTime>> timetableRequest;
}
