package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.TimetableEntry;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
class InstructorTimetablesOpt {

  private final long instructorId;
  @NonNull
  private final List<TimetableEntry> timetableEntries;
}
