package com.jusoft.slot;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

class SlotRepositoryInMemory implements SlotRepository {

    private final Map<Long, Slot> store;

    SlotRepositoryInMemory(Map<Long, Slot> store) {
        this.store = store;
    }

    @Override
    public List<Slot> getByRoom(long roomId) {
        return store.values().stream().filter(slot -> Long.compare(slot.getRoomId(), roomId) == 0).collect(toList());
    }

    @Override
    public Optional<Slot> find(long slotId, long roomId) {
        return Optional.ofNullable(store.get(slotId)).filter(slotFound -> Long.compare(slotFound.getRoomId(), roomId) == 0);
    }

    @Override
    public void save(Slot newSlot) {
        store.put(newSlot.getSlotId(), newSlot);
    }
}
