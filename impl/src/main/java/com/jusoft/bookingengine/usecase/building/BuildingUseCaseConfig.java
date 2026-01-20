package com.jusoft.bookingengine.usecase.building;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildingUseCaseConfig {

    @Autowired private ClubManagerComponent clubManagerComponent;

    @Autowired private BuildingManagerComponent buildingManagerComponent;

    @Bean
    public CreateBuildingUseCase createBuildingUseCase() {
        return new CreateBuildingUseCase(clubManagerComponent, buildingManagerComponent);
    }
}
