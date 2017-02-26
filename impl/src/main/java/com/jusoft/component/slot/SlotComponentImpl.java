package com.jusoft.component.slot;

import java.util.List;

class SlotComponentImpl implements SlotComponent {

    private final SlotRepository slotRepository;
    private final SlotFactory slotFactory;

    SlotComponentImpl(SlotRepository slotRepository, SlotFactory slotFactory) {
        this.slotRepository = slotRepository;
        this.slotFactory = slotFactory;
    }

    public Slot create(CreateSlotCommand createSlotCommand) {
        Slot newSlot = slotFactory.createFrom(createSlotCommand);
        slotRepository.save(newSlot);
        return newSlot;
    }

    public List<Slot> getSlotsFor(long roomId) {
        return slotRepository.getByRoom(roomId);
    }

    public Slot find(long slotId, long roomId) {
        return slotRepository.find(slotId, roomId).orElseThrow(() -> new SlotNotFoundException(slotId, roomId));
    }
}
