package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.RegisterRoomCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterRoomUseCase {

    private final ClassManagerComponent classManagerComponent;

    public void registerRoom(RegisterRoomCommand command) {
        classManagerComponent.registerRoom(command);
    }
}
