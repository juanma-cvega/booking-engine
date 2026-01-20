package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddRoomTagsToClubUseCase {

    private final AuthorizationManagerComponent authorizationManagerComponent;

    public void addRoomTagsToClub(AddRoomTagsToClubCommand command) {
        authorizationManagerComponent.addRoomTagsToClub(command);
    }
}
