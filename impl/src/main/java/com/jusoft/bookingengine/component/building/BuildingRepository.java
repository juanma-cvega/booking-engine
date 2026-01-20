package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.repository.Repository;
import java.util.Optional;

interface BuildingRepository extends Repository {

    void save(Building building);

    Optional<Building> find(long buildingId);
}
