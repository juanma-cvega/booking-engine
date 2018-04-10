package com.jusoft.bookingengine.component.building.api;

public interface BuildingManagerComponent {

  BuildingView create(CreateBuildingCommand command);

  BuildingView find(long id);

}
