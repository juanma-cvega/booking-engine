package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.repository.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

interface SlotRepository extends Repository {

    /**
     * Returns the {@link Slot} if it's contained in the store and if the roomId matches the one
     * provided
     *
     * @param slotId the id of the slot to findBySlot
     * @return Optional containing the {@link Slot} found
     */
    Optional<Slot> find(long slotId);

    /**
     * Stores the {@link Slot} provided
     *
     * @param newSlot {@link Slot} to store
     */
    void save(Slot newSlot);

    /**
     * Finds the last created {@link Slot} for the provided roomId
     *
     * @param roomId id of the room that contains the {@link Slot}
     * @return
     */
    Optional<Slot> getLastCreatedFor(long roomId);

    Optional<Slot> findSlotInUseOrToStartFor(long roomId);

    List<Slot> findOpenSlotsByRoom(long roomId);

    Slot execute(long slotId, UnaryOperator<Slot> operation);
}
