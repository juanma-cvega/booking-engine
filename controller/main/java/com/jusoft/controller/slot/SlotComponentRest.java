package com.jusoft.controller.slot;

import com.jusoft.component.slot.Slot;
import com.jusoft.component.slot.SlotComponent;
import com.jusoft.component.slot.SlotNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/slots", consumes = "application/json", produces = "application/json")
@Slf4j
class SlotComponentRest {

    private final SlotComponent slotComponent;
    private final SlotCommandFactory slotCommandFactory;
    private final SlotResourceFactory slotResourceFactory;

    SlotComponentRest(SlotComponent slotComponent, SlotCommandFactory slotCommandFactory, SlotResourceFactory slotResourceFactory) {
        this.slotComponent = slotComponent;
        this.slotCommandFactory = slotCommandFactory;
        this.slotResourceFactory = slotResourceFactory;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SlotResource create(@RequestBody CreateSlotRequest createSlotCommand) {
        log.info("Create slot request received: createSlotCommand={}", createSlotCommand);
        Slot slot = slotComponent.create(slotCommandFactory.createFrom(createSlotCommand));
        log.info("Create slot request finished: slot={}", slot);
        return createSlotResourceFactoryFrom(slot);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/room/{roomId}/slot/{slotId}")
    @ResponseBody
    public SlotResource find(@PathVariable Long roomId, @PathVariable Long slotId) {
        log.info("Get slot request received: roomId={}, slotId={}", roomId, slotId);
        Slot slot = slotComponent.find(slotId, roomId);
        log.info("Get slot request finished: slot={}", slot);
        return createSlotResourceFactoryFrom(slot);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/room/{roomId}")
    @ResponseBody
    public SlotResources getSlotsFor(@PathVariable Long roomId) {
        log.info("Get slots for room request received: roomId={}", roomId);
        List<Slot> slots = slotComponent.getSlotsFor(roomId);
        log.info("Get slots request finished: roomId={}, slots={}", roomId, slots.size());
        return slotResourceFactory.createFrom(slots);
    }

    private SlotResource createSlotResourceFactoryFrom(Slot slot) {
        return slotResourceFactory.createFrom(slot);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Slot not found in room")
    @ExceptionHandler(SlotNotFoundException.class)
    public void slotNotFoundException() {
    }
}
