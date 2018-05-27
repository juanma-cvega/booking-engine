package com.jusoft.bookingengine.component.building.api;

import java.util.Optional;

public interface BuildingManagerComponent {

  BuildingView create(CreateBuildingCommand command);

  Optional<BuildingView> find(long id);

}
