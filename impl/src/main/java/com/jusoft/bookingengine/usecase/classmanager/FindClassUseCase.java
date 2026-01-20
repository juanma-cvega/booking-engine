package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindClassUseCase {

    private final ClassManagerComponent classManagerComponent;

    public ClassView findClass(long classId) {
        return classManagerComponent.find(classId);
    }
}
