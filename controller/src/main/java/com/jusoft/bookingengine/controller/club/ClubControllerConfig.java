package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.usecase.club.CreateClubUseCase;
import com.jusoft.bookingengine.usecase.club.CreateJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.ReviewJoinRequestUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClubControllerConfig {

    @Bean
    public ClubControllerRest clubControllerRest(
            CreateClubUseCase createClubUseCase,
            CreateJoinRequestUseCase createJoinRequestUseCase,
            ReviewJoinRequestUseCase reviewJoinRequestUseCase) {
        return new ClubControllerRest(
                createClubUseCase, createJoinRequestUseCase, reviewJoinRequestUseCase);
    }
}
