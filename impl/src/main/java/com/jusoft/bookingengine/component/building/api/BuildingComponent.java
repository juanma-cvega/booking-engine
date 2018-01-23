package com.jusoft.bookingengine.component.building.api;

public interface BuildingComponent {

  BuildingView create(CreateBuildingCommand command);

  BuildingView find(long id);
}
