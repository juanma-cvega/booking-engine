package com.jusoft.bookingengine.component.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BuildingRepositoryInMemory implements BuildingRepository {

  private final Map<Long, Building> store;

  @Override
  public void save(Building building) {
    store.put(building.getId(), building);
  }

  @Override
  public Optional<Building> find(long buildingId) {
    return Optional.ofNullable(store.get(buildingId));
  }
}
