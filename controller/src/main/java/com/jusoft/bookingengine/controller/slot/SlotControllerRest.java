package com.jusoft.bookingengine.controller.slot;

import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.controller.slot.api.CreateSlotRequest;
import com.jusoft.bookingengine.usecase.slot.CreateSlotUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/slots")
class SlotControllerRest {

    private final CreateSlotUseCase createSlotUseCase;
    private final SlotResourceFactory slotResourceFactory;

    SlotControllerRest(
            CreateSlotUseCase createSlotUseCase, SlotResourceFactory slotResourceFactory) {
        this.createSlotUseCase = createSlotUseCase;
        this.slotResourceFactory = slotResourceFactory;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SlotResource create(@Valid @RequestBody CreateSlotRequest request) {
        SlotView slot = createSlotUseCase.createSlotFor(request.roomId());
        return slotResourceFactory.createFrom(slot);
    }
}
