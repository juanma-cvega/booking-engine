package com.jusoft.bookingengine.controller.slot;

import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.CREATE_SLOT_REQUEST;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.END_TIME;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_RESOURCE_1;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_VIEW_1;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.START_TIME;
import static com.jusoft.bookingengine.util.HelpUtils.OBJECT_MAPPER;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jusoft.bookingengine.component.room.api.RoomNotFoundException;
import com.jusoft.bookingengine.controller.GlobalExceptionHandler;
import com.jusoft.bookingengine.controller.slot.api.CreateSlotRequest;
import com.jusoft.bookingengine.usecase.slot.CreateSlotUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class SlotControllerRestTest {

    private static final String CREATE_SLOT_URL = "/slots";

    @Mock private CreateSlotUseCase mockCreateSlotUseCase;

    @Mock private SlotResourceFactory mockSlotResourceFactory;

    @InjectMocks private SlotControllerRest slotControllerRest;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(slotControllerRest)
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    void createSlot() throws Exception {
        when(mockCreateSlotUseCase.createSlotFor(ROOM_ID)).thenReturn(SLOT_VIEW_1);
        when(mockSlotResourceFactory.createFrom(SLOT_VIEW_1)).thenReturn(SLOT_RESOURCE_1);

        mockMvc.perform(
                        post(CREATE_SLOT_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_SLOT_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slotId", is((int) SLOT_ID_1)))
                .andExpect(jsonPath("$.roomId", is((int) ROOM_ID)))
                .andExpect(jsonPath("$.startDate", is((int) START_TIME)))
                .andExpect(jsonPath("$.endDate", is((int) END_TIME)));
    }

    @Test
    void createSlotWithNullRoomIdFails() throws Exception {
        mockMvc.perform(
                        post(CREATE_SLOT_URL)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new CreateSlotRequest(null))))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mockCreateSlotUseCase);
    }

    @Test
    void createSlotForUnknownRoomFails() throws Exception {
        when(mockCreateSlotUseCase.createSlotFor(ROOM_ID))
                .thenThrow(new RoomNotFoundException(ROOM_ID));

        mockMvc.perform(
                        post(CREATE_SLOT_URL)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_SLOT_REQUEST)))
                .andExpect(status().isNotFound());
    }
}
