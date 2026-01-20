package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindClassesByBuildingUseCase {

    private final ClassManagerComponent classManagerComponent;

    public List<ClassView> findClassesBy(long buildingId) {
        return classManagerComponent.findByBuilding(buildingId);
    }
}
