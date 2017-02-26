package com.jusoft.controller.slot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jusoft.component.slot.SlotComponent;
import com.jusoft.component.slot.SlotNotFoundException;
import com.jusoft.fixtures.SlotControllerFixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.StringJoiner;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.slot.SlotsFixtures.*;
import static com.jusoft.fixtures.SlotControllerFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SlotControllerRestTest {

    private static final String SLOTS_URL = "/slots";
    private static final String FIND_URL_TEMPLATE = "/room/%s/slot/%s";
    private static final String GET_SLOTS_FOR_URL_TEMPLATE = "room/%s";
    private static final java.lang.CharSequence FORTHSLASH = "/";

    @Mock
    private SlotComponent mockSlotComponent;
    @Mock
    private SlotCommandFactory mockSlotCommandFactory;
    @Mock
    private SlotResourceFactory mockSlotResourceFactory;

    @InjectMocks
    private SlotControllerRest slotControllerRest;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(slotControllerRest).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void create() throws Exception {
        when(mockSlotCommandFactory.createFrom(CREATE_SLOT_REQUEST)).thenReturn(CREATE_SLOT_COMMAND);
        when(mockSlotComponent.create(CREATE_SLOT_COMMAND)).thenReturn(SLOT_1);
        when(mockSlotResourceFactory.createFrom(SLOT_1)).thenReturn(SLOT_RESOURCE_1);

        mockMvc.perform(post(SLOTS_URL).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(CREATE_SLOT_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.startDate", is(Long.valueOf(SlotControllerFixtures.START_TIME).intValue())))
                .andExpect(jsonPath("$.endDate", is(Long.valueOf(SlotControllerFixtures.END_TIME).intValue())));
    }

    @Test
    public void find() throws Exception {
        when(mockSlotComponent.find(SLOT_ID_1, ROOM_ID)).thenReturn(SLOT_1);
        when(mockSlotResourceFactory.createFrom(SLOT_1)).thenReturn(SLOT_RESOURCE_1);

        String findUrl = String.format(FIND_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(SLOTS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.startDate", is(Long.valueOf(SlotControllerFixtures.START_TIME).intValue())))
                .andExpect(jsonPath("$.endDate", is(Long.valueOf(SlotControllerFixtures.END_TIME).intValue())));
    }

    @Test
    public void getSlotsFor() throws Exception {
        when(mockSlotComponent.getSlotsFor(ROOM_ID)).thenReturn(SLOTS);
        when(mockSlotResourceFactory.createFrom(SLOTS)).thenReturn(SLOT_RESOURCES);

        String getSlotsForUrl = String.format(GET_SLOTS_FOR_URL_TEMPLATE, ROOM_ID);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(SLOTS_URL).add(getSlotsForUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slots[0].slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.slots[0].roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.slots[0].startDate", is(Long.valueOf(SlotControllerFixtures.START_TIME).intValue())))
                .andExpect(jsonPath("$.slots[0].endDate", is(Long.valueOf(SlotControllerFixtures.END_TIME).intValue())))
                .andExpect(jsonPath("$.slots[1].slotId", is(Long.valueOf(SLOT_ID_2).intValue())))
                .andExpect(jsonPath("$.slots[1].roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.slots[1].startDate", is(Long.valueOf(SlotControllerFixtures.START_TIME).intValue())))
                .andExpect(jsonPath("$.slots[1].endDate", is(Long.valueOf(SlotControllerFixtures.END_TIME).intValue())));
    }

    @Test
    public void slotNotFoundException() throws Exception {
        when(mockSlotComponent.find(SLOT_ID_1, ROOM_ID)).thenThrow(new SlotNotFoundException(SLOT_ID_1, ROOM_ID));

        String findUrl = String.format(FIND_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(SLOTS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Slot not found in room"));
    }
}
