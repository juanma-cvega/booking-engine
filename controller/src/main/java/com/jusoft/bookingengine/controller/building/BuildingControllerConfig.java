package com.jusoft.bookingengine.controller.building;

import com.jusoft.bookingengine.usecase.building.CreateBuildingUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildingControllerConfig {

    @Bean
    public BuildingControllerRest buildingControllerRest(
            CreateBuildingUseCase createBuildingUseCase) {
        return new BuildingControllerRest(createBuildingUseCase);
    }
}
