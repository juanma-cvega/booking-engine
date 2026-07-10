package com.jusoft.bookingengine.controller.building;

import com.jusoft.bookingengine.controller.building.api.BuildingResource;
import com.jusoft.bookingengine.controller.building.api.CreateBuildingRequest;
import com.jusoft.bookingengine.usecase.building.CreateBuildingUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/buildings")
class BuildingControllerRest {

    private final CreateBuildingUseCase createBuildingUseCase;
    private final BuildingCommandFactory buildingCommandFactory;
    private final BuildingResourceFactory buildingResourceFactory;

    BuildingControllerRest(
            CreateBuildingUseCase createBuildingUseCase,
            BuildingCommandFactory buildingCommandFactory,
            BuildingResourceFactory buildingResourceFactory) {
        this.createBuildingUseCase = createBuildingUseCase;
        this.buildingCommandFactory = buildingCommandFactory;
        this.buildingResourceFactory = buildingResourceFactory;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BuildingResource create(@Valid @RequestBody CreateBuildingRequest request) {
        return buildingResourceFactory.createFrom(
                createBuildingUseCase.createBuildingFrom(
                        buildingCommandFactory.createFrom(request)));
    }
}
