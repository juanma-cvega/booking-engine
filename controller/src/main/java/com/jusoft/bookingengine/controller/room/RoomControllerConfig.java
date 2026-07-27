package com.jusoft.bookingengine.controller.room;

import com.jusoft.bookingengine.usecase.room.CreateRoomUseCase;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomControllerConfig {

    @Bean
    public RoomControllerRest roomControllerRest(CreateRoomUseCase createRoomUseCase, Clock clock) {
        return new RoomControllerRest(createRoomUseCase, clock);
    }
}
