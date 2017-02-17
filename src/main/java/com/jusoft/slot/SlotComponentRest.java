package com.jusoft.slot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/slots")
public class SlotComponentRest implements SlotComponent {

    private final SlotComponent slotComponent;

    public SlotComponentRest(SlotComponent slotComponent) {
        this.slotComponent = slotComponent;
    }

    @Override
    public void create(CreateSlotRequest createSlotRequest) {

    }

    @Override
    @RequestMapping(consumes = "application/json", produces = "application/json", method = RequestMethod.GET, value = "/room/{roomId}/slot/{slotId}")
    public SlotResource find(@PathVariable Long roomId, @PathVariable Long slotId) {
        return slotComponent.find(slotId, roomId);
    }

    @Override
    @RequestMapping(consumes = "application/json", produces = "application/json", method = RequestMethod.GET, value = "/room/{roomId}")
    public List<SlotResource> getSlotsFor(@PathVariable Long roomId) {
        return slotComponent.getSlotsFor(roomId);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Slot not found")
    @ExceptionHandler(SlotNotFoundException.class)
    public void slotNotFoundException() {

    }
}
