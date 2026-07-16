package com.jusoft.bookingengine.controller.room;

import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.CREATE_ROOM_COMMAND;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.CREATE_ROOM_REQUEST;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.FIXED_CLOCK;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.MAX_SLOTS;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.OPEN_TIME_REQUESTS;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_VIEW;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.SLOT_DURATION_IN_MINUTES;
import static com.jusoft.bookingengine.util.HelpUtils.OBJECT_MAPPER;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jusoft.bookingengine.component.building.api.BuildingNotFoundException;
import com.jusoft.bookingengine.controller.GlobalExceptionHandler;
import com.jusoft.bookingengine.controller.room.api.CreateRoomRequest;
import com.jusoft.bookingengine.controller.room.api.OpenTimeRequest;
import com.jusoft.bookingengine.usecase.room.CreateRoomUseCase;
import java.time.DayOfWeek;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RoomControllerRestTest {

    private static final String ROOMS_URL = "/rooms";

    @Mock private CreateRoomUseCase mockCreateRoomUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        RoomControllerRest roomControllerRest =
                new RoomControllerRest(
                        mockCreateRoomUseCase,
                        new RoomCommandFactory(FIXED_CLOCK),
                        new RoomResourceFactory());
        mockMvc =
                MockMvcBuilders.standaloneSetup(roomControllerRest)
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    void createRoom() throws Exception {
        when(mockCreateRoomUseCase.createRoom(CREATE_ROOM_COMMAND)).thenReturn(ROOM_VIEW);

        mockMvc.perform(
                        post(ROOMS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_ROOM_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomId", is((int) ROOM_ID)))
                .andExpect(jsonPath("$.clubId", is((int) CLUB_ID)))
                .andExpect(jsonPath("$.buildingId", is((int) BUILDING_ID)))
                .andExpect(jsonPath("$.slotDurationInMinutes", is(SLOT_DURATION_IN_MINUTES)))
                .andExpect(jsonPath("$.maxSlots", is(MAX_SLOTS)))
                .andExpect(jsonPath("$.openTimes[0].startTime", is("08:00")))
                .andExpect(jsonPath("$.openTimes[0].endTime", is("12:00")))
                .andExpect(jsonPath("$.openTimes[1].startTime", is("14:00")))
                .andExpect(jsonPath("$.openTimes[1].endTime", is("16:00")))
                .andExpect(jsonPath("$.openTimes[2].startTime", is("20:00")))
                .andExpect(jsonPath("$.openTimes[2].endTime", is("22:00")))
                .andExpect(jsonPath("$.availableDays[0]", is(DayOfWeek.MONDAY.name())))
                .andExpect(jsonPath("$.availableDays[1]", is(DayOfWeek.TUESDAY.name())));
    }

    @Test
    void createRoomWithMissingBuildingIdFails() throws Exception {
        CreateRoomRequest requestWithoutBuilding =
                new CreateRoomRequest(
                        null,
                        SLOT_DURATION_IN_MINUTES,
                        MAX_SLOTS,
                        OPEN_TIME_REQUESTS,
                        AVAILABLE_DAYS);

        mockMvc.perform(
                        post(ROOMS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(requestWithoutBuilding)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRoomWithMissingMaxSlotsFails() throws Exception {
        CreateRoomRequest requestWithoutMaxSlots =
                new CreateRoomRequest(
                        BUILDING_ID,
                        SLOT_DURATION_IN_MINUTES,
                        null,
                        OPEN_TIME_REQUESTS,
                        AVAILABLE_DAYS);

        mockMvc.perform(
                        post(ROOMS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(requestWithoutMaxSlots)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRoomWithNullOpenTimeStartFails() throws Exception {
        CreateRoomRequest requestWithNullOpenTimeStart =
                new CreateRoomRequest(
                        BUILDING_ID,
                        SLOT_DURATION_IN_MINUTES,
                        MAX_SLOTS,
                        List.of(new OpenTimeRequest(null, "12:00")),
                        AVAILABLE_DAYS);

        mockMvc.perform(
                        post(ROOMS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                requestWithNullOpenTimeStart)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRoomWithMalformedOpenTimeFails() throws Exception {
        CreateRoomRequest requestWithMalformedOpenTime =
                new CreateRoomRequest(
                        BUILDING_ID,
                        SLOT_DURATION_IN_MINUTES,
                        MAX_SLOTS,
                        List.of(new OpenTimeRequest("25:00", "26:00")),
                        AVAILABLE_DAYS);

        mockMvc.perform(
                        post(ROOMS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                requestWithMalformedOpenTime)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRoomForUnknownBuildingFails() throws Exception {
        when(mockCreateRoomUseCase.createRoom(CREATE_ROOM_COMMAND))
                .thenThrow(new BuildingNotFoundException(BUILDING_ID));

        mockMvc.perform(
                        post(ROOMS_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_ROOM_REQUEST)))
                .andExpect(status().isNotFound());
    }
}
