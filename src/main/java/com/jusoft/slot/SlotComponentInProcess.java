package com.jusoft.slot;

public class SlotComponentInProcess implements SlotComponent {

    private final SlotService slotService;
    private final SlotResourceFactory slotResourceFactory;

    public SlotComponentInProcess(SlotService slotService, SlotResourceFactory slotResourceFactory) {
        this.slotService = slotService;
        this.slotResourceFactory = slotResourceFactory;
    }

    @Override
    public SlotResource create(CreateSlotRequest createSlotRequest) {
        Slot slotCreated = slotService.create(createSlotRequest);
        return slotResourceFactory.createFrom(slotCreated);
    }

    @Override
    public SlotResource find(Long slotId, Long roomId) {
        return slotService.find(slotId, roomId).map(slotResourceFactory::createFrom).orElseThrow(() -> new SlotNotFoundException(slotId, roomId));
    }

    @Override
    public SlotResources getSlotsFor(Long roomId) {
        return slotResourceFactory.createFrom(slotService.getSlotsFor(roomId));
    }
}
