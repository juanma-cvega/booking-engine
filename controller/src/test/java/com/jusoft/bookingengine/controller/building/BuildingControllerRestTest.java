package com.jusoft.bookingengine.controller.building;

import static com.jusoft.bookingengine.fixtures.BuildingFixtures.BUILDING_DESCRIPTION;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.BUILDING_VIEW;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.CITY;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.CREATE_BUILDING_COMMAND;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.CREATE_BUILDING_REQUEST;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.STREET;
import static com.jusoft.bookingengine.fixtures.BuildingFixtures.ZIP_CODE;
import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.util.HelpUtils.OBJECT_MAPPER;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import com.jusoft.bookingengine.controller.GlobalExceptionHandler;
import com.jusoft.bookingengine.controller.building.api.CreateBuildingRequest;
import com.jusoft.bookingengine.usecase.building.CreateBuildingUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BuildingControllerRestTest {

    private static final String BUILDINGS_URL = "/buildings";

    @Mock private CreateBuildingUseCase mockCreateBuildingUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        BuildingControllerRest buildingControllerRest =
                new BuildingControllerRest(
                        mockCreateBuildingUseCase,
                        new BuildingCommandFactory(),
                        new BuildingResourceFactory());
        mockMvc =
                MockMvcBuilders.standaloneSetup(buildingControllerRest)
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    void createBuilding() throws Exception {
        when(mockCreateBuildingUseCase.createBuildingFrom(CREATE_BUILDING_COMMAND))
                .thenReturn(BUILDING_VIEW);

        mockMvc.perform(
                        post(BUILDINGS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_BUILDING_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.buildingId", is((int) BUILDING_ID)))
                .andExpect(jsonPath("$.clubId", is((int) CLUB_ID)))
                .andExpect(jsonPath("$.street", is(STREET)))
                .andExpect(jsonPath("$.zipCode", is(ZIP_CODE)))
                .andExpect(jsonPath("$.city", is(CITY)))
                .andExpect(jsonPath("$.description", is(BUILDING_DESCRIPTION)));
    }

    @Test
    void createBuildingWithMissingClubIdFails() throws Exception {
        mockMvc.perform(
                        post(BUILDINGS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new CreateBuildingRequest(
                                                        null,
                                                        STREET,
                                                        ZIP_CODE,
                                                        CITY,
                                                        BUILDING_DESCRIPTION))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBuildingForUnknownClubFails() throws Exception {
        when(mockCreateBuildingUseCase.createBuildingFrom(CREATE_BUILDING_COMMAND))
                .thenThrow(new ClubNotFoundException(CLUB_ID));

        mockMvc.perform(
                        post(BUILDINGS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_BUILDING_REQUEST)))
                .andExpect(status().isNotFound());
    }
}
