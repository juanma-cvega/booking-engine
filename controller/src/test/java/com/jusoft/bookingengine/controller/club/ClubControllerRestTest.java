package com.jusoft.bookingengine.controller.club;

import static com.jusoft.bookingengine.fixtures.ClubFixtures.ACCEPT_JOIN_REQUEST_COMMAND;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.ADMIN_ID;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_DESCRIPTION;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_NAME;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_VIEW;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CREATE_CLUB_COMMAND;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CREATE_CLUB_REQUEST;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CREATE_JOIN_REQUEST_COMMAND;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CREATE_JOIN_REQUEST_REQUEST;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.DENY_JOIN_REQUEST_COMMAND;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.JOIN_REQUEST;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.JOIN_REQUEST_ID;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.REVIEW_JOIN_REQUEST_REQUEST;
import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.util.HelpUtils.OBJECT_MAPPER;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.club.api.JoinRequestNotFoundException;
import com.jusoft.bookingengine.controller.GlobalExceptionHandler;
import com.jusoft.bookingengine.controller.club.api.CreateClubRequest;
import com.jusoft.bookingengine.controller.club.api.CreateJoinRequestRequest;
import com.jusoft.bookingengine.controller.club.api.ReviewJoinRequestRequest;
import com.jusoft.bookingengine.usecase.club.AcceptJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.CreateClubUseCase;
import com.jusoft.bookingengine.usecase.club.CreateJoinRequestUseCase;
import com.jusoft.bookingengine.usecase.club.DenyJoinRequestUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ClubControllerRestTest {

    private static final String CLUBS_URL = "/clubs";
    private static final String JOIN_REQUESTS_URL_TEMPLATE = "/clubs/%s/join-requests";
    private static final String ACCEPT_JOIN_REQUEST_URL_TEMPLATE =
            "/clubs/%s/join-requests/%s/accept";
    private static final String DENY_JOIN_REQUEST_URL_TEMPLATE = "/clubs/%s/join-requests/%s/deny";

    @Mock private CreateClubUseCase mockCreateClubUseCase;

    @Mock private CreateJoinRequestUseCase mockCreateJoinRequestUseCase;

    @Mock private AcceptJoinRequestUseCase mockAcceptJoinRequestUseCase;

    @Mock private DenyJoinRequestUseCase mockDenyJoinRequestUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        ClubControllerRest clubControllerRest =
                new ClubControllerRest(
                        mockCreateClubUseCase,
                        mockCreateJoinRequestUseCase,
                        mockAcceptJoinRequestUseCase,
                        mockDenyJoinRequestUseCase,
                        new ClubCommandFactory(),
                        new ClubResourceFactory());
        mockMvc =
                MockMvcBuilders.standaloneSetup(clubControllerRest)
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    void createClub() throws Exception {
        when(mockCreateClubUseCase.createClubFrom(CREATE_CLUB_COMMAND)).thenReturn(CLUB_VIEW);

        mockMvc.perform(
                        post(CLUBS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_CLUB_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clubId", is((int) CLUB_ID)))
                .andExpect(jsonPath("$.name", is(CLUB_NAME)))
                .andExpect(jsonPath("$.description", is(CLUB_DESCRIPTION)))
                .andExpect(jsonPath("$.admins[0]", is((int) ADMIN_ID)));
    }

    @Test
    void createClubWithNullNameFails() throws Exception {
        mockMvc.perform(
                        post(CLUBS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new CreateClubRequest(
                                                        null, CLUB_DESCRIPTION, ADMIN_ID))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createClubWithNullAdminIdFails() throws Exception {
        mockMvc.perform(
                        post(CLUBS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new CreateClubRequest(
                                                        CLUB_NAME, CLUB_DESCRIPTION, null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createJoinRequest() throws Exception {
        when(mockCreateJoinRequestUseCase.createJoinRequest(CREATE_JOIN_REQUEST_COMMAND))
                .thenReturn(JOIN_REQUEST);

        mockMvc.perform(
                        post(String.format(JOIN_REQUESTS_URL_TEMPLATE, CLUB_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                CREATE_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.joinRequestId", is((int) JOIN_REQUEST_ID)))
                .andExpect(jsonPath("$.userId", is(USER_ID_1.intValue())));
    }

    @Test
    void createJoinRequestWithNullUserIdFails() throws Exception {
        mockMvc.perform(
                        post(String.format(JOIN_REQUESTS_URL_TEMPLATE, CLUB_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new CreateJoinRequestRequest(null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createJoinRequestForUnknownClubIsNotFound() throws Exception {
        when(mockCreateJoinRequestUseCase.createJoinRequest(CREATE_JOIN_REQUEST_COMMAND))
                .thenThrow(new ClubNotFoundException(CLUB_ID));

        mockMvc.perform(
                        post(String.format(JOIN_REQUESTS_URL_TEMPLATE, CLUB_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                CREATE_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isNotFound());
    }

    @Test
    void acceptJoinRequest() throws Exception {
        mockMvc.perform(
                        post(String.format(
                                        ACCEPT_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                REVIEW_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isNoContent());

        verify(mockAcceptJoinRequestUseCase).acceptJoinRequest(ACCEPT_JOIN_REQUEST_COMMAND);
    }

    @Test
    void acceptJoinRequestWithNullAdminIdFails() throws Exception {
        mockMvc.perform(
                        post(String.format(
                                        ACCEPT_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new ReviewJoinRequestRequest(null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void acceptJoinRequestByNonAdminIsForbidden() throws Exception {
        doThrow(new ClubAuthorizationException(ADMIN_ID, CLUB_ID))
                .when(mockAcceptJoinRequestUseCase)
                .acceptJoinRequest(ACCEPT_JOIN_REQUEST_COMMAND);

        mockMvc.perform(
                        post(String.format(
                                        ACCEPT_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                REVIEW_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void acceptUnknownJoinRequestIsNotFound() throws Exception {
        doThrow(new JoinRequestNotFoundException(JOIN_REQUEST_ID, CLUB_ID))
                .when(mockAcceptJoinRequestUseCase)
                .acceptJoinRequest(ACCEPT_JOIN_REQUEST_COMMAND);

        mockMvc.perform(
                        post(String.format(
                                        ACCEPT_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                REVIEW_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isNotFound());
    }

    @Test
    void denyJoinRequest() throws Exception {
        mockMvc.perform(
                        post(String.format(
                                        DENY_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                REVIEW_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isNoContent());

        verify(mockDenyJoinRequestUseCase).denyJoinRequest(DENY_JOIN_REQUEST_COMMAND);
    }

    @Test
    void denyJoinRequestWithNullAdminIdFails() throws Exception {
        mockMvc.perform(
                        post(String.format(
                                        DENY_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new ReviewJoinRequestRequest(null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void denyJoinRequestByNonAdminIsForbidden() throws Exception {
        doThrow(new ClubAuthorizationException(ADMIN_ID, CLUB_ID))
                .when(mockDenyJoinRequestUseCase)
                .denyJoinRequest(DENY_JOIN_REQUEST_COMMAND);

        mockMvc.perform(
                        post(String.format(
                                        DENY_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                REVIEW_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void denyUnknownJoinRequestIsNotFound() throws Exception {
        doThrow(new JoinRequestNotFoundException(JOIN_REQUEST_ID, CLUB_ID))
                .when(mockDenyJoinRequestUseCase)
                .denyJoinRequest(DENY_JOIN_REQUEST_COMMAND);

        mockMvc.perform(
                        post(String.format(
                                        DENY_JOIN_REQUEST_URL_TEMPLATE, CLUB_ID, JOIN_REQUEST_ID))
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                REVIEW_JOIN_REQUEST_REQUEST)))
                .andExpect(status().isNotFound());
    }
}
