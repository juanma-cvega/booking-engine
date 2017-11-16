package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.room.Room;

import java.util.List;
import java.util.Optional;

interface SlotRepository {

  /**
   * Returns the list of {@link Slot}s contained in the room with the id provided
   *
   * @param roomId id of the room the {@link Slot}s belong to
   * @return List of {@link Slot}s found
   */
  List<Slot> getByRoom(long roomId);

  /**
   * Returns the {@link Slot} if it's contained in the store and if the roomId matches the one provided
   *
   * @param slotId the id of the slot to findBySlot
   * @param roomId the id of the room the slot belongs to
   * @return Optional containing the {@link Slot} found
   */
  Optional<Slot> find(long slotId, long roomId);

  /**
   * Stores the {@link Slot} provided
   *
   * @param newSlot {@link Slot} to store
   */
  void save(Slot newSlot);

  /**
   * Finds the last created {@link Slot} for the provided roomId
   *
   * @param roomId id of the {@link Room} that contains the {@link Slot}
   * @return
   */
  Optional<Slot> getLastCreatedFor(long roomId);

  Optional<Slot> findSlotInUseOrToStartFor(long roomId);
}
