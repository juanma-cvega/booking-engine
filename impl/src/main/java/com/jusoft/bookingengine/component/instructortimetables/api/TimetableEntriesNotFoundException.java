package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Getter;

import java.util.List;

@Getter
public class TimetableEntriesNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -5338874766930331103L;

  private static final String MESSAGE = "Instructor %s does not contain entries %s";

  private final long instructorId;
  private final transient List<TimetableEntry> entriesNotFound;
  private final transient List<TimetableEntry> remainingEntries;

  public TimetableEntriesNotFoundException(long instructorId, List<TimetableEntry> entriesNotFound, List<TimetableEntry> remainingEntries) {
    super(String.format(MESSAGE, instructorId, entriesNotFound));
    this.instructorId = instructorId;
    this.entriesNotFound = entriesNotFound;
    this.remainingEntries = remainingEntries;
  }
}
