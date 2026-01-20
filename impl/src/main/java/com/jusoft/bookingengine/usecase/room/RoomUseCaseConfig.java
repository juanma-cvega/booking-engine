package com.jusoft.bookingengine.usecase.room;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomUseCaseConfig {

    @Autowired private RoomManagerComponent roomManagerComponent;

    @Autowired private BuildingManagerComponent buildingManagerComponent;

    @Bean
    public CreateRoomUseCase createRoomUseCase() {
        return new CreateRoomUseCase(roomManagerComponent, buildingManagerComponent);
    }
}
