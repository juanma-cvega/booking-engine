package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.controller.club.api.ClubResource;
import com.jusoft.bookingengine.controller.club.api.CreateClubRequest;
import com.jusoft.bookingengine.controller.club.api.CreateJoinRequestRequest;
import com.jusoft.bookingengine.controller.club.api.JoinRequestResource;
import com.jusoft.bookingengine.controller.club.api.ReviewJoinRequestRequest;
import com.jusoft.bookingengine.usecase.club.CreateClubUseCase;
import com.jusoft.bookingengine.usecase.club.CreateJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.ReviewJoinRequestUseCase;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/clubs")
class ClubControllerRest {

    private final CreateClubUseCase createClubUseCase;
    private final CreateJoinRequestUseCase createJoinRequestUseCase;
    private final ReviewJoinRequestUseCase reviewJoinRequestUseCase;
    private final ClubCommandFactory clubCommandFactory;
    private final ClubResourceFactory clubResourceFactory;

    ClubControllerRest(
            CreateClubUseCase createClubUseCase,
            CreateJoinRequestUseCase createJoinRequestUseCase,
            ReviewJoinRequestUseCase reviewJoinRequestUseCase,
            ClubCommandFactory clubCommandFactory,
            ClubResourceFactory clubResourceFactory) {
        this.createClubUseCase = createClubUseCase;
        this.createJoinRequestUseCase = createJoinRequestUseCase;
        this.reviewJoinRequestUseCase = reviewJoinRequestUseCase;
        this.clubCommandFactory = clubCommandFactory;
        this.clubResourceFactory = clubResourceFactory;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ClubResource create(@Valid @RequestBody CreateClubRequest request) {
        log.info(
                "Create club request received: name={}, adminId={}",
                request.name(),
                request.adminId());
        ClubView club =
                createClubUseCase.createClubFrom(
                        clubCommandFactory.createClubCommandFrom(
                                request.name(), request.description(), request.adminId()));
        ClubResource clubResource = clubResourceFactory.createFrom(club);
        log.info("Create club request finished: club={}", clubResource);
        return clubResource;
    }

    @PostMapping(
            value = "/{clubId}/join-requests",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public JoinRequestResource createJoinRequest(
            @PathVariable long clubId, @Valid @RequestBody CreateJoinRequestRequest request) {
        log.info("Create join request received: clubId={}, userId={}", clubId, request.userId());
        JoinRequest joinRequest =
                createJoinRequestUseCase.createJoinRequest(
                        clubCommandFactory.createJoinRequestCommandFrom(clubId, request.userId()));
        JoinRequestResource joinRequestResource = clubResourceFactory.createFrom(joinRequest);
        log.info("Create join request finished: joinRequest={}", joinRequestResource);
        return joinRequestResource;
    }

    @PatchMapping(value = "/{clubId}/join-requests/{joinRequestId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void review(
            @PathVariable long clubId,
            @PathVariable long joinRequestId,
            @Valid @RequestBody ReviewJoinRequestRequest request) {
        log.info(
                "Review join request received: clubId={}, joinRequestId={}, adminId={}, decision={}",
                clubId,
                joinRequestId,
                request.adminId(),
                request.decision());
        reviewJoinRequestUseCase.review(
                clubCommandFactory.reviewJoinRequestCommandFrom(
                        joinRequestId, clubId, request.adminId(), request.decision()));
        log.info("Review join request finished: joinRequestId={}", joinRequestId);
    }
}
