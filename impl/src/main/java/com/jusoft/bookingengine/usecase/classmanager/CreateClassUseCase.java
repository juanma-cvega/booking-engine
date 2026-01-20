package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.CreateClassCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateClassUseCase {

    private final ClassManagerComponent classManagerComponent;

    public ClassView createClass(CreateClassCommand command) {
        return classManagerComponent.create(command);
    }
}
