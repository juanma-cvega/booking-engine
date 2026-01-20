package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.RegisterRoomCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class RegisterRoomUseCase {

    @Autowired private ClassManagerComponent classManagerComponent;

    public void registerRoom(RegisterRoomCommand command) {
        classManagerComponent.registerRoom(command);
    }
}
