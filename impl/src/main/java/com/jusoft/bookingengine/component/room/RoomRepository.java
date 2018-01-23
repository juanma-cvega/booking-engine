package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;

interface RoomRepository extends Repository<Room> {

  void save(Room newRoom);

  Optional<Room> find(long roomId);
}
