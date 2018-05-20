package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.Timetable;
import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Data(staticConstructor = "of")
class RoomTimetable {

  private final long roomId;

  @NonNull
  private final Map<String, Timetable> timetables;

  static RoomTimetable of(long roomId) {
    return of(roomId, new HashMap<>());
  }

  boolean addTimetable(Timetable timetable) {
    boolean result = false;
    if (!isOverlappingWithFiltering(timetable, byClassTypeDifferentFrom(timetable))) {
      timetables.put(timetable.getClassType(), timetable);
      result = true;
    }
    return result;
  }

  boolean isOverlappingWith(Timetable timetable) {
    Predicate<Timetable> identity = x -> true;
    return isOverlappingWithFiltering(timetable, identity);
  }

  private boolean isOverlappingWithFiltering(Timetable timetable, Predicate<Timetable> filters) {
    return timetables.values().stream()
      .filter(filters)
      .anyMatch(timetableSource -> areOverlapping(timetableSource, timetable));
  }

  private Predicate<Timetable> byClassTypeDifferentFrom(Timetable timetable) {
    return sourceTimetable -> !sourceTimetable.getClassType().equals(timetable.getClassType());
  }

  private boolean areOverlapping(Timetable first, Timetable second) {
    return first.getDaysTimetable().keySet().stream()
      .anyMatch(dayOfWeek -> areOverlapping(first.getDaysTimetable().get(dayOfWeek), second.getDaysTimetable().get(dayOfWeek)));
  }

  private boolean areOverlapping(List<OpenTime> firstOpenTimes, List<OpenTime> secondOpenTimes) {
    return firstOpenTimes.stream()
      .anyMatch(firstOpenTime -> secondOpenTimes.stream()
        .anyMatch(secondOpenTime -> secondOpenTime.isOverlappingWith(firstOpenTime)));
  }

  boolean removeTimetable(Timetable timetable) {
    return timetables.remove(timetable.getClassType()) != null;
  }

  Map<String, Timetable> getTimetables() {
    return new HashMap<>(timetables);
  }
}
