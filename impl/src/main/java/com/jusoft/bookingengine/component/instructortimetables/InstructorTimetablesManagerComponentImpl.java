package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.AddTimetableEntriesCommand;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.RemoveTimetableEntriesCommand;
import com.jusoft.bookingengine.component.instructortimetables.api.TimetableEntry;
import com.jusoft.bookingengine.component.timer.OpenTime;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
class InstructorTimetablesManagerComponentImpl implements InstructorTimetablesManagerComponent {

  private final InstructorTimetablesManagerRepository repository;

  @Override
  public void registerInstructor(long instructorId) {
    InstructorTimetables newInstructorTimetables = new InstructorTimetables(instructorId);
    repository.save(newInstructorTimetables);
  }

  @Override
  public void unregisterInstructor(long instructorId) {
    repository.delete(instructorId);
  }

  @Override
  public Optional<InstructorTimetablesView> find(long instructorId) {
    return repository.find(instructorId).map(this::createFrom);
  }

  @Override
  public void addTimetable(AddTimetableEntriesCommand command) {
    List<TimetableEntry> newEntries = command.getSlotsByDayOfWeek().entrySet().stream()
      .map(entry -> toTimetableEntriesByDayOfWeek(command, entry))
      .flatMap(List::stream)
      .collect(Collectors.toList());
    repository.execute(command.getInstructorId(), instructorTimetables ->
      instructorTimetables.addTimetableEntries(newEntries));
  }

  private List<TimetableEntry> toTimetableEntriesByDayOfWeek(AddTimetableEntriesCommand command, Map.Entry<DayOfWeek, List<OpenTime>> entry) {
    return entry.getValue().stream()
      .map(openTime -> TimetableEntry.of(command.getBuildingId(), command.getRoomId(), command.getClassType(), entry.getKey(), openTime))
      .collect(Collectors.toList());
  }

  @Override
  public void removeTimetable(RemoveTimetableEntriesCommand command) {
    repository.execute(command.getInstructorId(), instructorTimetables ->
      instructorTimetables.removeTimetableEntries(command.getTimetableEntries()));
  }

  private InstructorTimetablesView createFrom(InstructorTimetables newInstructorTimetables) {
    return InstructorTimetablesView.of(newInstructorTimetables.getInstructorId(), newInstructorTimetables.getTimetableEntries());
  }
}
