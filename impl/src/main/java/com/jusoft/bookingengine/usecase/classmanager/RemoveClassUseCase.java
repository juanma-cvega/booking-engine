package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveClassUseCase {

    private final ClassManagerComponent classManagerComponent;

    public void removeClass(long classId) {
        classManagerComponent.remove(classId);
    }
}
