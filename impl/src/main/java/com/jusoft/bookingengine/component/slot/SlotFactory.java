package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotFactory {

    private final Supplier<Long> idGenerator;
    private final Clock clock;
    private final SlotStateFactory slotStateFactory;

    Slot createFrom(CreateSlotCommand request) {
        return new Slot(
                idGenerator.get(),
                request.roomId(),
                request.buildingId(),
                request.clubId(),
                ZonedDateTime.now(clock),
                request.openDate());
    }

    SlotView createFrom(Slot slot) {
        return new SlotView(
                slot.getId(),
                slot.getRoomId(),
                slot.getBuildingId(),
                slot.getClubId(),
                slotStateFactory.getSlotStateFor(slot.getState()),
                slot.getCreationTime(),
                slot.getOpenDate());
    }

    public List<SlotView> createFrom(List<Slot> slots) {
        return slots.stream().map(this::createFrom).collect(Collectors.toList());
    }
}
