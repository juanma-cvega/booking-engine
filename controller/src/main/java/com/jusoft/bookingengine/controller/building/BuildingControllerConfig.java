package com.jusoft.bookingengine.controller.building;

import com.jusoft.bookingengine.usecase.building.CreateBuildingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildingControllerConfig {

    @Autowired private CreateBuildingUseCase createBuildingUseCase;

    @Bean
    public BuildingControllerRest buildingControllerRest() {
        return new BuildingControllerRest(
                createBuildingUseCase, buildingCommandFactory(), buildingResourceFactory());
    }

    private BuildingCommandFactory buildingCommandFactory() {
        return new BuildingCommandFactory();
    }

    private BuildingResourceFactory buildingResourceFactory() {
        return new BuildingResourceFactory();
    }
}
