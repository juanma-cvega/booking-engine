package com.jusoft.bookingengine.component.room;

import java.util.Optional;

interface RoomRepository {

  void save(Room newRoom);

  Optional<Room> find(long roomId);
}
