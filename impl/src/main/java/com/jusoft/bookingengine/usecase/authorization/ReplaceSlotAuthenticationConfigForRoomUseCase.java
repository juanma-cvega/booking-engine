package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ReplaceSlotAuthenticationConfigForRoomCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReplaceSlotAuthenticationConfigForRoomUseCase {

    private final AuthorizationManagerComponent authorizationManagerComponent;

    public void replaceSlotAuthenticationConfigForRoom(
            ReplaceSlotAuthenticationConfigForRoomCommand command) {
        authorizationManagerComponent.replaceSlotAuthenticationManagerForRoom(command);
    }
}
