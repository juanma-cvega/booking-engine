package com.jusoft.bookingengine.component.building;

import java.util.Optional;

interface BuildingRepository {

  void save(Building building);

  Optional<Building> find(long buildingId);
}
