package com.jusoft.bookingengine.controller.room;

import com.jusoft.bookingengine.usecase.room.CreateRoomUseCase;
import java.time.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomControllerConfig {

    @Autowired private CreateRoomUseCase createRoomUseCase;

    @Autowired private Clock clock;

    @Bean
    public RoomControllerRest roomControllerRest() {
        return new RoomControllerRest(
                createRoomUseCase, roomCommandFactory(), roomResourceFactory());
    }

    private RoomCommandFactory roomCommandFactory() {
        return new RoomCommandFactory(clock);
    }

    private RoomResourceFactory roomResourceFactory() {
        return new RoomResourceFactory();
    }
}
