package com.jusoft.slot;

import java.util.List;
import java.util.Optional;

class SlotService {

    private final SlotRepository slotRepository;
    private final SlotFactory slotFactory;

    SlotService(SlotRepository slotRepository, SlotFactory slotFactory) {
        this.slotRepository = slotRepository;
        this.slotFactory = slotFactory;
    }

    void create(CreateSlotRequest createSlotRequest) {
        slotRepository.save(slotFactory.createFrom(createSlotRequest));
    }

    List<Slot> getSlotsFor(long roomId) {
        return slotRepository.getByRoom(roomId);
    }

    Optional<Slot> find(long slotId, long roomId) {
        return slotRepository.find(slotId, roomId);
    }
}
