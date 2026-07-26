package com.jusoft.bookingengine.controller.building;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
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

    BuildingControllerRest(CreateBuildingUseCase createBuildingUseCase) {
        this.createBuildingUseCase = createBuildingUseCase;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BuildingResource create(@Valid @RequestBody CreateBuildingRequest request) {
        BuildingView building =
                createBuildingUseCase.createBuildingFrom(
                        new CreateBuildingCommand(
                                request.clubId(), request.toAddress(), request.description()));
        return new BuildingResource(
                building.id(),
                building.clubId(),
                building.address().getStreet(),
                building.address().getZipCode(),
                building.address().getCity(),
                building.description());
    }
}
