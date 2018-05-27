package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
public class AddTimetableToInstructorUseCaseCommand implements Command {

  private final long instructorId;
  private final long roomId;
  private final Map<DayOfWeek, List<SystemLocalTime>> slotsStartTimeByDayOfWeek;
}
