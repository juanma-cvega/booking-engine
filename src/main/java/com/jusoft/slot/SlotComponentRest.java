package com.jusoft.slot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/slots", consumes = "application/json", produces = "application/json")
@Slf4j
public class SlotComponentRest implements SlotComponent {

    private final SlotComponent slotComponent;

    public SlotComponentRest(SlotComponent slotComponent) {
        this.slotComponent = slotComponent;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SlotResource create(@RequestBody CreateSlotRequest createSlotRequest) {
        log.info("Create slot request received: createSlotRequest={}", createSlotRequest);
        SlotResource slotResource = slotComponent.create(createSlotRequest);
        log.info("Create slot request finished: slot={}", slotResource);
        return slotResource;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/room/{roomId}/slot/{slotId}")
    @ResponseBody
    public SlotResource find(@PathVariable Long roomId, @PathVariable Long slotId) {
        log.info("Get slot request received: roomId={}, slotId={}", roomId, slotId);
        SlotResource slotResource = slotComponent.find(slotId, roomId);
        log.info("Get slot request finished: slot={}", slotResource);
        return slotResource;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/room/{roomId}")
    @ResponseBody
    public SlotResources getSlotsFor(@PathVariable Long roomId) {
        log.info("Get slots for room request received: roomId={}", roomId);
        SlotResources slots = slotComponent.getSlotsFor(roomId);
        log.info("Get slots request finished: roomId={}, slots={}", roomId, slots.getSlots().size());
        return slots;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Slot not found in room")
    @ExceptionHandler(SlotNotFoundException.class)
    public void slotNotFoundException() {
    }
}
