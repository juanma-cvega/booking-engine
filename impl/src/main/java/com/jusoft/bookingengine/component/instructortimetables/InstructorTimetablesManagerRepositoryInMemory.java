package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.Timetable;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesNotFoundException;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.SearchCriteriaCommand;
import com.jusoft.bookingengine.repository.AbstractRepositoryInMemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class InstructorTimetablesManagerRepositoryInMemory extends AbstractRepositoryInMemory<Long, InstructorTimetables> implements InstructorTimetablesManagerRepository {

  InstructorTimetablesManagerRepositoryInMemory(ConcurrentMap<Long, InstructorTimetables> store) {
    super(store);
  }

  @Override
  public void save(InstructorTimetables instructorTimetables) {
    super.save(instructorTimetables);
  }

  @Override
  public void delete(long instructorId) {
    super.deleteIf(instructorId, instructorTimetables -> true);
  }

  @Override
  public Optional<InstructorTimetables> find(long instructorId) {
    return super.find(instructorId);
  }

  @Override
  public void execute(long instructorId, UnaryOperator<InstructorTimetables> modifier) {
    super.execute(instructorId, modifier);
  }

  @Override
  public InstructorTimetables findByInstructorAndBuilding(long instructorId, long buildingId) {
    return null;
//    return store.getOrDefault(instructorId, new InstructorTimetables(instructorId)).getBuildingsTimetables();
  }

  @Override
  public InstructorTimetables findByInstructorAndRoom(long instructorId, long roomId) {
    return null;
  }

  @Override
  public Optional<InstructorTimetablesView> findBy(SearchCriteriaCommand criteria) {
    return Optional.ofNullable(store.get(criteria.getInstructorId())).map(instructorTimetables -> {
      Map<Long, BuildingTimetables> filteredBuildingsTimetable = filterBuildingTimetables(instructorTimetables, criteria);

      InstructorTimetablesView.of(
        instructorTimetables.getId(),
        filteredBuildingsTimetable);
    })
  }

  private Map<Long, BuildingTimetables> filterBuildingTimetables(InstructorTimetables instructorTimetables, SearchCriteriaCommand command) {
    Map<Long, BuildingTimetables> buildingsTimetables = instructorTimetables.getBuildingsTimetables();
    return command.getBuildingsId().stream()
      .filter(buildingsTimetables::containsKey)
      .collect(Collectors.toMap(Function.identity(), buildingsTimetables::get));
  }

  private Map<Long, RoomTimetable> filterRoomTimetables(BuildingTimetables buildingTimetables, SearchCriteriaCommand command) {
    Map<Long, RoomTimetable> roomsTimetables = buildingTimetables.getRoomsTimetable();
    return command.getRoomsId().stream()
      .filter(roomsTimetables::containsKey)
      .collect(Collectors.toMap(Function.identity(), roomsTimetables::get));
  }

  private Map<String, Timetable> filterClassTypesTimetables(RoomTimetable roomTimetable, SearchCriteriaCommand command) {
    Map<String, Timetable> classesTimetable = roomTimetable.getTimetables();
    return command.getClassTypes().stream()
      .filter(classesTimetable::containsKey)
      .collect(Collectors.toMap(Function.identity(), classesTimetable::get));
  }

  @Override
  protected Long getIdFrom(InstructorTimetables entity) {
    return entity.getId();
  }

  @Override
  protected RuntimeException createNotFoundException(Long entityId) {
    return new InstructorTimetablesNotFoundException(entityId);
  }
}
