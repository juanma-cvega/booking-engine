package com.jusoft.bookingengine.component.room;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomRepositoryInMemory implements RoomRepository {

  private final Map<Long, Room> store;

  @Override
  public void save(Room newRoom) {
    store.put(newRoom.getId(), newRoom);
  }

  @Override
  public Optional<Room> find(long roomId) {
    Room value = store.get(roomId);
    if (value != null) {
      return Optional.of(new Room(value.getId(),
        value.getBuildingId(),
        value.getSlotCreationConfigInfo(),
        value.getSlotDurationInMinutes(),
        value.getOpenTimesPerDay(),
        value.getAvailableDays(),
        value.isActive(),
        value.getAuctionConfigInfo()));
    }
    return Optional.empty();
  }
}
