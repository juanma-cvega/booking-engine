package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
public class AddTimetableEntriesCommand implements Command {

  private final long instructorId;
  private final long roomId;
  private final long buildingId;
  private final String classType;
  private final Map<DayOfWeek, List<OpenTime>> slotsByDayOfWeek;
}
