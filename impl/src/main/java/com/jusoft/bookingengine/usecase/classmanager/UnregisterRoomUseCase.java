package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.UnregisterRoomCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class UnregisterRoomUseCase {

    @Autowired private final ClassManagerComponent classManagerComponent;

    public void unregisterRoom(UnregisterRoomCommand command) {
        classManagerComponent.unregisterRoom(command);
    }
}
