package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.component.building.api.BuildingCreatedEvent;
import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingNotFoundException;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BuildingManagerComponentImpl implements BuildingManagerComponent {

    private final BuildingFactory buildingFactory;
    private final BuildingRepository repository;
    private final MessagePublisher messagePublisher;

    @Override
    public BuildingView create(CreateBuildingCommand command) {
        Building building = buildingFactory.createFrom(command);
        repository.save(building);
        messagePublisher.publish(
                BuildingCreatedEvent.of(
                        building.getId(), building.getAddress(), building.getDescription()));
        return buildingFactory.createFrom(building);
    }

    @Override
    public BuildingView find(long id) {
        return buildingFactory.createFrom(
                repository.find(id).orElseThrow(() -> new BuildingNotFoundException(id)));
    }
}
