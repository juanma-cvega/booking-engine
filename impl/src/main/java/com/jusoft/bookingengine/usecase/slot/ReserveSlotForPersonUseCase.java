package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotForPersonUseCase {

    private static final String USER_TYPE_PERSON = "PERSON";

    private final AuthorizationManagerComponent authorizationManagerComponent;
    private final SlotManagerComponent slotManagerComponent;

    public void reserveSlotForPerson(long slotId, long userId) {
        SlotView slot = slotManagerComponent.find(slotId);
        authorizationManagerComponent.authorizeReserveSlot(
                AuthorizeCommand.of(
                        userId,
                        slot.roomId(),
                        slot.buildingId(),
                        slot.clubId(),
                        slot.creationTime()));
        slotManagerComponent.reserveSlot(slotId, new SlotUser(userId, USER_TYPE_PERSON));
    }
}
