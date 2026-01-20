package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.CreateClassCommand;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClassFactory {

    private final Supplier<Long> idSupplier;

    Class createFrom(CreateClassCommand command) {
        return new Class(
                idSupplier.get(),
                command.getBuildingId(),
                command.getDescription(),
                command.getInstructorsId(),
                command.getType());
    }

    ClassView createFrom(Class classSource) {
        return ClassView.of(
                classSource.getId(),
                classSource.getBuildingId(),
                classSource.getDescription(),
                classSource.getInstructorsId(),
                classSource.getClassType(),
                classSource.getRoomsRegistered());
    }
}
