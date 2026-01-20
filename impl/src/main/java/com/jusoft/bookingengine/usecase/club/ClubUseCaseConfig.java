package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClubUseCaseConfig {

    @Autowired private ClubManagerComponent clubManagerComponent;

    @Bean
    public CreateClubUseCase createClubUseCase() {
        return new CreateClubUseCase(clubManagerComponent);
    }

    @Bean
    public AcceptJoinRequestUseCase acceptJoinRequestUseCase() {
        return new AcceptJoinRequestUseCase(clubManagerComponent);
    }

    @Bean
    public DenyJoinRequestUseCase denyJoinRequestUseCase() {
        return new DenyJoinRequestUseCase(clubManagerComponent);
    }

    @Bean
    public FindClubByNameUseCase findClubByNameUseCase() {
        return new FindClubByNameUseCase(clubManagerComponent);
    }

    @Bean
    public CreateJoinRequestUseCase createJoinRequestUseCase() {
        return new CreateJoinRequestUseCase(clubManagerComponent);
    }

    @Bean
    public FindJoinRequestsUseCase findJoinRequestsUseCase() {
        return new FindJoinRequestsUseCase(clubManagerComponent);
    }
}
