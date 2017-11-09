package com.jusoft.component.room;

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
        return Optional.ofNullable(store.get(roomId));
    }
}
