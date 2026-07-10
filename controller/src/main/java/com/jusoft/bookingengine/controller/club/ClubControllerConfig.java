package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.usecase.club.AcceptJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.CreateClubUseCase;
import com.jusoft.bookingengine.usecase.club.CreateJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.DenyJoinRequestUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClubControllerConfig {

    @Autowired private CreateClubUseCase createClubUseCase;

    @Autowired private CreateJoinRequestUseCase createJoinRequestUseCase;

    @Autowired private AcceptJoinRequestUseCase acceptJoinRequestUseCase;

    @Autowired private DenyJoinRequestUseCase denyJoinRequestUseCase;

    @Bean
    public ClubControllerRest clubControllerRest() {
        return new ClubControllerRest(
                createClubUseCase,
                createJoinRequestUseCase,
                acceptJoinRequestUseCase,
                denyJoinRequestUseCase,
                clubCommandFactory(),
                clubResourceFactory());
    }

    private ClubCommandFactory clubCommandFactory() {
        return new ClubCommandFactory();
    }

    private ClubResourceFactory clubResourceFactory() {
        return new ClubResourceFactory();
    }
}
