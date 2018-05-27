package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Getter
public class TimetableEntryOverlappingException extends RuntimeException {

  private static final long serialVersionUID = 8577481476859747500L;

  private static final String MESSAGE = "Instructor %s has overlapping entries %s ";

  private final long instructorId;
  private final transient List<Pair<TimetableEntry, TimetableEntry>> overlappingEntries;

  public TimetableEntryOverlappingException(long instructorId, List<Pair<TimetableEntry, TimetableEntry>> overlappingEntries) {
    super(String.format(MESSAGE, instructorId, overlappingEntries));
    this.instructorId = instructorId;
    this.overlappingEntries = overlappingEntries;
  }
}
