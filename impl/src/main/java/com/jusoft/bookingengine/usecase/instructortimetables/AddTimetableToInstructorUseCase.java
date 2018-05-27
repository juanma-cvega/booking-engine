package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructortimetables.api.AddTimetableEntriesCommand;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import com.jusoft.bookingengine.component.room.api.GetTimetableCommand;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomTimetable;
import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class AddTimetableToInstructorUseCase {

  private final InstructorTimetablesManagerComponent instructorTimetablesManagerComponent;
  private final InstructorManagerComponent instructorManagerComponent;
  private final RoomManagerComponent roomManagerComponent;

  public void addTimetable(AddTimetableToInstructorUseCaseCommand command) {
    //FIXME validate user issuing the request is admin of building the room belongs to
    RoomTimetable roomTimetable = roomManagerComponent
      .getTimetableFor(GetTimetableCommand.of(command.getRoomId(), command.getSlotsStartTimeByDayOfWeek()));
    //FIXME find out buildingId from roomId
    instructorTimetablesManagerComponent.addTimetable(
      AddTimetableEntriesCommand.of(command.getInstructorId(), command.getRoomId(), roomTimetable.getTimetable()));
  }

  //Makes use of the fact that all days of the week have the same valid slot start times.
  private Set<SystemLocalTime> getAllSlotsStartTimeFrom(Map<DayOfWeek, List<SystemLocalTime>> slotsStartTimeByDayOfWeek) {
    return slotsStartTimeByDayOfWeek.values().stream()
      .flatMap(List::stream)
      .collect(toSet());
  }
}
