package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindClassesByBuildingUseCase {

  private final ClassManagerComponent classManagerComponent;

  public List<ClassView> findClassesBy(long buildingId) {
    return classManagerComponent.findByBuilding(buildingId);
  }
}
