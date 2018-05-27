package com.jusoft.bookingengine.component.instructortimetables;


import com.jusoft.bookingengine.component.instructortimetables.api.TimetableEntriesNotFoundException;
import com.jusoft.bookingengine.component.instructortimetables.api.TimetableEntry;
import com.jusoft.bookingengine.component.instructortimetables.api.TimetableEntryOverlappingException;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;

@Data
class InstructorTimetables {

  private final long instructorId;
  private final List<TimetableEntry> timetableEntries;

  InstructorTimetables(long instructorId) {
    this(instructorId, new ArrayList<>());
  }

  InstructorTimetables(long instructorId, List<TimetableEntry> timetableEntries) {
    Objects.requireNonNull(timetableEntries);
    this.instructorId = instructorId;
    this.timetableEntries = new ArrayList<>(timetableEntries);
  }

  InstructorTimetables addTimetableEntries(List<TimetableEntry> newEntries) {
    List<TimetableEntry> newTimetableEntries = createNewSortedEntryListWith(newEntries);

    verifyTimetableEntriesAreValid(newTimetableEntries);
    return new InstructorTimetables(instructorId, newTimetableEntries);
  }

  private List<TimetableEntry> createNewSortedEntryListWith(List<TimetableEntry> newEntries) {
    List<TimetableEntry> newTimetableEntries = new ArrayList<>(this.timetableEntries);
    newTimetableEntries.addAll(newEntries);
    newTimetableEntries.sort(comparing(TimetableEntry::getDayOfWeek).thenComparing(TimetableEntry::getClassPeriod));
    return newTimetableEntries;
  }

  private void verifyTimetableEntriesAreValid(List<TimetableEntry> timetableEntries) {
    List<Pair<TimetableEntry, TimetableEntry>> overlappingEntries = findOverlappingEntriesIn(timetableEntries);
    if (!overlappingEntries.isEmpty()) {
      throw new TimetableEntryOverlappingException(instructorId, overlappingEntries);
    }
  }

  private List<Pair<TimetableEntry, TimetableEntry>> findOverlappingEntriesIn(List<TimetableEntry> timetableEntries) {
    List<Pair<TimetableEntry, TimetableEntry>> overlappingEntries = new ArrayList<>();
    for (int index = 0; index < timetableEntries.size() - 1; index++) {
      TimetableEntry firstEntry = timetableEntries.get(index);
      TimetableEntry secondEntry = timetableEntries.get(index + 1);
      if (firstEntry.getDayOfWeek().equals(secondEntry.getDayOfWeek())
        && firstEntry.getClassPeriod().isOverlappingWith(secondEntry.getClassPeriod())) {
        overlappingEntries.add(Pair.of(firstEntry, secondEntry));
      }
    }
    return overlappingEntries;
  }

  InstructorTimetables removeTimetableEntries(List<TimetableEntry> entriesToRemove) {
    List<TimetableEntry> remainingEntries = new ArrayList<>(timetableEntries);
    List<TimetableEntry> notRemovedEntries = new ArrayList<>();
    for (TimetableEntry entry : entriesToRemove) {
      if (!remainingEntries.remove(entry)) {
        notRemovedEntries.add(entry);
      }
    }
    if (!notRemovedEntries.isEmpty()) {
      throw new TimetableEntriesNotFoundException(instructorId, notRemovedEntries, remainingEntries);
    }
    return new InstructorTimetables(instructorId, remainingEntries);
  }
}
