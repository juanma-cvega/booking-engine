package com.jusoft.component.room;

import java.util.Optional;

public interface RoomRepository {
    void save(Room newRoom);

    Optional<Room> find(long roomId);
}
