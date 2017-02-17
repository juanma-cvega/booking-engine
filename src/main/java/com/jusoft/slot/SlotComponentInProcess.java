package com.jusoft.slot;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SlotComponentInProcess implements SlotComponent {

    private final SlotService slotService;
    private final SlotResourceFactory slotResourceFactory;

    public SlotComponentInProcess(SlotService slotService, SlotResourceFactory slotResourceFactory) {
        this.slotService = slotService;
        this.slotResourceFactory = slotResourceFactory;
    }

    @Override
    public void create(CreateSlotRequest createSlotRequest) {
        slotService.create(createSlotRequest);
    }

    @Override
    public SlotResource find(Long slotId, Long roomId) {
        return slotService.find(slotId, roomId).map(slotResourceFactory::createFrom).orElseThrow(() -> new SlotNotFoundException(slotId));
    }

    @Override
    public List<SlotResource> getSlotsFor(Long roomId) {
        return slotService.getSlotsFor(roomId).stream().map(slotResourceFactory::createFrom).collect(toList());
    }
}
