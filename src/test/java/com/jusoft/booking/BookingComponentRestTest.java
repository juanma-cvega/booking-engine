package com.jusoft.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import static com.jusoft.fixtures.BookingFixtures.*;
import static com.jusoft.fixtures.CommonFixtures.*;
import static com.jusoft.fixtures.SlotsFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookingComponentRestTest {

    private static final CharSequence FORTHSLASH = "/";
    private static final String BOOKINGS_URL = "/bookings";
    private static final String BOOKING_URL_TEMPLATE = "/user/%s/booking/%s";
    private static final String GET_FOR_URL_TEMPLATE = "/user/%s";
    @Mock
    private BookingComponent mockBookingComponent;

    @InjectMocks
    private BookingComponentRest bookingComponentRest;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookingComponentRest).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void book() throws Exception {
        when(mockBookingComponent.book(CREATE_BOOKING_REQUEST)).thenReturn(BOOKING_RESOURCE_1);

        mockMvc.perform(post(BOOKINGS_URL).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId", is(Long.valueOf(BOOKING_ID_1).intValue())))
                .andExpect(jsonPath("$.bookingTime", is(Long.valueOf(BOOKING_TIME).intValue())))
                .andExpect(jsonPath("$.slot.slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.slot.roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.slot.startDate", is(Long.valueOf(START_TIME).intValue())))
                .andExpect(jsonPath("$.slot.endDate", is(Long.valueOf(END_TIME).intValue())));
    }

    @Test
    public void cancel() throws Exception {
        String cancelUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(delete(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mockBookingComponent).cancel(USER_ID_1, BOOKING_ID_1);
    }

    @Test
    public void find() throws Exception {
        when(mockBookingComponent.find(USER_ID_1, BOOKING_ID_1)).thenReturn(BOOKING_RESOURCE_1);

        String findUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId", is(Long.valueOf(BOOKING_ID_1).intValue())))
                .andExpect(jsonPath("$.bookingTime", is(Long.valueOf(BOOKING_TIME).intValue())))
                .andExpect(jsonPath("$.slot.slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.slot.roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.slot.startDate", is(Long.valueOf(START_TIME).intValue())))
                .andExpect(jsonPath("$.slot.endDate", is(Long.valueOf(END_TIME).intValue())));
    }

    @Test
    public void getFor() throws Exception {
        when(mockBookingComponent.getFor(USER_ID_1)).thenReturn(BOOKING_RESOURCES);

        String cancelUrl = String.format(GET_FOR_URL_TEMPLATE, USER_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookings[0].bookingId", is(Long.valueOf(BOOKING_ID_1).intValue())))
                .andExpect(jsonPath("$.bookings[0].bookingTime", is(Long.valueOf(BOOKING_TIME).intValue())))
                .andExpect(jsonPath("$.bookings[0].slot.slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.bookings[0].slot.roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.bookings[0].slot.startDate", is(Long.valueOf(START_TIME).intValue())))
                .andExpect(jsonPath("$.bookings[0].slot.endDate", is(Long.valueOf(END_TIME).intValue())))
                .andExpect(jsonPath("$.bookings[1].bookingId", is(Long.valueOf(BOOKING_ID_2).intValue())))
                .andExpect(jsonPath("$.bookings[1].bookingTime", is(Long.valueOf(BOOKING_TIME).intValue())))
                .andExpect(jsonPath("$.bookings[1].slot.slotId", is(Long.valueOf(SLOT_ID_1).intValue())))
                .andExpect(jsonPath("$.bookings[1].slot.roomId", is(Long.valueOf(ROOM_ID).intValue())))
                .andExpect(jsonPath("$.bookings[1].slot.startDate", is(Long.valueOf(START_TIME).intValue())))
                .andExpect(jsonPath("$.bookings[1].slot.endDate", is(Long.valueOf(END_TIME).intValue())));
    }

    @Test
    public void bookingNotFoundException() throws Exception {
        when(mockBookingComponent.find(USER_ID_1, BOOKING_ID_1)).thenThrow(new BookingNotFoundException(USER_ID_1, BOOKING_ID_1));

        String findUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Booking not found"));
    }

    @Test
    public void slotAlreadyBooked() throws Exception {
        when(mockBookingComponent.book(CREATE_BOOKING_REQUEST)).thenThrow(new SlotAlreadyBookedException(ROOM_ID, SLOT_ID_1));

        mockMvc.perform(post(BOOKINGS_URL).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isConflict())
                .andExpect(status().reason("Slot already booked"));
    }

    @Test
    public void slotAlreadyStarted() throws Exception {
        when(mockBookingComponent.book(CREATE_BOOKING_REQUEST)).thenThrow(new SlotAlreadyStartedException(SLOT_ID_1, ROOM_ID, LocalDateTime.now()));

        mockMvc.perform(post(BOOKINGS_URL).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isPreconditionRequired())
                .andExpect(status().reason("Slot already started"));
    }

    @Test
    public void wrongBookingUser() throws Exception {
        when(mockBookingComponent.book(CREATE_BOOKING_REQUEST)).thenThrow(new WrongBookingUserException(USER_ID_1, USER_ID_2, BOOKING_ID_1));

        mockMvc.perform(post(BOOKINGS_URL).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Booking does not belong to user"));
    }
}
