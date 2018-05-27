package com.jusoft.bookingengine.component.instructortimetables.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class RemoveTimetableEntriesCommand implements Command {

  private final long instructorId;
  private final List<TimetableEntry> timetableEntries;
}
