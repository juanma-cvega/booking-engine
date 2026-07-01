package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.UnregisterRoomCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnregisterRoomUseCase {

    private final ClassManagerComponent classManagerComponent;

    public void unregisterRoom(UnregisterRoomCommand command) {
        classManagerComponent.unregisterRoom(command);
    }
}
