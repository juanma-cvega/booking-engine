package com.jusoft.bookingengine.component.building.api;

public interface BuildingComponent {

  BuildingView create(CreateBuildingCommand command);

  boolean isAvailable(long buildingId);

  BuildingView find(long id);
}
