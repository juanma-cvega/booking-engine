package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.controller.club.api.ClubResource;
import com.jusoft.bookingengine.controller.club.api.CreateClubRequest;
import com.jusoft.bookingengine.controller.club.api.CreateJoinRequestRequest;
import com.jusoft.bookingengine.controller.club.api.JoinRequestResource;
import com.jusoft.bookingengine.controller.club.api.ReviewJoinRequestRequest;
import com.jusoft.bookingengine.usecase.club.AcceptJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.CreateClubUseCase;
import com.jusoft.bookingengine.usecase.club.CreateJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.DenyJoinRequestUseCase;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final AcceptJoinRequestUseCase acceptJoinRequestUseCase;
    private final DenyJoinRequestUseCase denyJoinRequestUseCase;
    private final ClubCommandFactory clubCommandFactory;
    private final ClubResourceFactory clubResourceFactory;

    ClubControllerRest(
            CreateClubUseCase createClubUseCase,
            CreateJoinRequestUseCase createJoinRequestUseCase,
            AcceptJoinRequestUseCase acceptJoinRequestUseCase,
            DenyJoinRequestUseCase denyJoinRequestUseCase,
            ClubCommandFactory clubCommandFactory,
            ClubResourceFactory clubResourceFactory) {
        this.createClubUseCase = createClubUseCase;
        this.createJoinRequestUseCase = createJoinRequestUseCase;
        this.acceptJoinRequestUseCase = acceptJoinRequestUseCase;
        this.denyJoinRequestUseCase = denyJoinRequestUseCase;
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

    @PostMapping(
            value = "/{clubId}/join-requests/{joinRequestId}/accept",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accept(
            @PathVariable long clubId,
            @PathVariable long joinRequestId,
            @Valid @RequestBody ReviewJoinRequestRequest request) {
        log.info(
                "Accept join request received: clubId={}, joinRequestId={}, adminId={}",
                clubId,
                joinRequestId,
                request.adminId());
        acceptJoinRequestUseCase.acceptJoinRequest(
                clubCommandFactory.acceptJoinRequestCommandFrom(
                        joinRequestId, clubId, request.adminId()));
        log.info("Accept join request finished: joinRequestId={}", joinRequestId);
    }

    @PostMapping(
            value = "/{clubId}/join-requests/{joinRequestId}/deny",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deny(
            @PathVariable long clubId,
            @PathVariable long joinRequestId,
            @Valid @RequestBody ReviewJoinRequestRequest request) {
        log.info(
                "Deny join request received: clubId={}, joinRequestId={}, adminId={}",
                clubId,
                joinRequestId,
                request.adminId());
        denyJoinRequestUseCase.denyJoinRequest(
                clubCommandFactory.denyJoinRequestCommandFrom(
                        joinRequestId, clubId, request.adminId()));
        log.info("Deny join request finished: joinRequestId={}", joinRequestId);
    }
}
