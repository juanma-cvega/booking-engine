package com.jusoft.bookingengine.component.building;

import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BuildingRepositoryInMemory implements BuildingRepository {

    private final Map<Long, Building> store;

    @Override
    public void save(Building building) {
        store.put(building.getId(), building);
    }

    @Override
    public Optional<Building> find(long buildingId) {
        Building value = store.get(buildingId);
        if (value != null) {
            return Optional.of(
                    new Building(
                            value.getId(),
                            value.getClubId(),
                            value.getAddress(),
                            value.getDescription()));
        }
        return Optional.empty();
    }
}
