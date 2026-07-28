package com.jusoft.bookingengine.controller.booking;

import static com.jusoft.bookingengine.fixtures.BookingFixtures.BOOKINGS;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.BOOKING_1;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.BOOKING_ID_1;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.BOOKING_ID_2;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.BOOKING_TIME;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.CREATE_BOOKING_COMMAND;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.CREATE_BOOKING_REQUEST;
import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_2;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.ANOTHER_SLOT_USER;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_ID_2;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_USER;
import static com.jusoft.bookingengine.util.HelpUtils.OBJECT_MAPPER;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import com.jusoft.bookingengine.controller.GlobalExceptionHandler;
import com.jusoft.bookingengine.controller.booking.api.CreateBookingRequest;
import com.jusoft.bookingengine.usecase.booking.CancelBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.CreateBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.FindBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.GetBookingsUseCase;
import java.util.StringJoiner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BookingControllerRestTest {

    private static final CharSequence FORTHSLASH = "/";
    private static final String BOOKINGS_URL = "/bookings";
    private static final String CREATE_BOOKING_URL_TEMPLATE = "/room/%s/slot/%s/booking";
    private static final String BOOKING_URL_TEMPLATE = "/user/%s/booking/%s";
    private static final String GET_FOR_URL_TEMPLATE = "/user/%s";

    @Mock private CreateBookingUseCase mockCreateBookingUseCase;

    @Mock private CancelBookingUseCase mockCancelBookingUseCase;

    @Mock private FindBookingUseCase mockFindBookingUseCase;

    @Mock private GetBookingsUseCase mockGetBookingsUseCase;

    @InjectMocks private BookingControllerRest bookingControllerRest;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(bookingControllerRest)
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    void createBooking() throws Exception {
        when(mockCreateBookingUseCase.book(CREATE_BOOKING_COMMAND)).thenReturn(BOOKING_1);

        String createUrl = String.format(CREATE_BOOKING_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(createUrl).toString();
        mockMvc.perform(
                        post(urlTemplate)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId", is((int) BOOKING_ID_1)))
                .andExpect(jsonPath("$.userId", is(USER_ID_1.intValue())))
                .andExpect(jsonPath("$.bookingTime", is((int) BOOKING_TIME)))
                .andExpect(jsonPath("$.slotId", is((int) SLOT_ID_1)));
    }

    @Test
    void cancelBooking() throws Exception {
        String cancelUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(delete(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mockCancelBookingUseCase).cancel(USER_ID_1, BOOKING_ID_1);
    }

    @Test
    void cancelBookingForSlotNotOpenFails() throws Exception {
        doThrow(new SlotNotOpenException(SLOT_ID_1))
                .when(mockCancelBookingUseCase)
                .cancel(USER_ID_1, BOOKING_ID_1);

        String cancelUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(delete(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isPreconditionRequired());
    }

    @Test
    void cancelBookingForUnknownBookingFails() throws Exception {
        doThrow(new BookingNotFoundException(BOOKING_ID_1))
                .when(mockCancelBookingUseCase)
                .cancel(USER_ID_1, BOOKING_ID_1);

        String cancelUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(delete(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void cancelBookingForWrongUserFails() throws Exception {
        doThrow(new WrongBookingUserException(USER_ID_1, USER_ID_2, BOOKING_ID_1))
                .when(mockCancelBookingUseCase)
                .cancel(USER_ID_1, BOOKING_ID_1);

        String cancelUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(delete(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBooking() throws Exception {
        when(mockFindBookingUseCase.find(USER_ID_1, BOOKING_ID_1)).thenReturn(BOOKING_1);

        String findUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId", is((int) BOOKING_ID_1)))
                .andExpect(jsonPath("$.userId", is(USER_ID_1.intValue())))
                .andExpect(jsonPath("$.bookingTime", is((int) BOOKING_TIME)))
                .andExpect(jsonPath("$.slotId", is((int) SLOT_ID_1)));
    }

    @Test
    void getBookingsForUser() throws Exception {
        when(mockGetBookingsUseCase.getBookingsFor(USER_ID_1)).thenReturn(BOOKINGS);

        String cancelUrl = String.format(GET_FOR_URL_TEMPLATE, USER_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(cancelUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookings[0].bookingId", is((int) BOOKING_ID_1)))
                .andExpect(jsonPath("$.bookings[0].userId", is(USER_ID_1.intValue())))
                .andExpect(jsonPath("$.bookings[0].bookingTime", is((int) BOOKING_TIME)))
                .andExpect(jsonPath("$.bookings[0].slotId", is((int) SLOT_ID_1)))
                .andExpect(jsonPath("$.bookings[1].bookingId", is((int) BOOKING_ID_2)))
                .andExpect(jsonPath("$.bookings[1].userId", is(USER_ID_1.intValue())))
                .andExpect(jsonPath("$.bookings[1].bookingTime", is((int) BOOKING_TIME)))
                .andExpect(jsonPath("$.bookings[1].slotId", is((int) SLOT_ID_2)));
    }

    @Test
    void createBookingWithNullUserIdFails() throws Exception {
        String createUrl = String.format(CREATE_BOOKING_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(createUrl).toString();
        mockMvc.perform(
                        post(urlTemplate)
                                .contentType(APPLICATION_JSON)
                                .content(
                                        OBJECT_MAPPER.writeValueAsString(
                                                new CreateBookingRequest(null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findBookingForWrongUserFails() throws Exception {
        doThrow(new WrongBookingUserException(USER_ID_2, USER_ID_1, BOOKING_ID_1))
                .when(mockFindBookingUseCase)
                .find(USER_ID_2, BOOKING_ID_1);

        String findUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_2, BOOKING_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBookingForUnknownBookingFails() throws Exception {
        when(mockFindBookingUseCase.find(USER_ID_1, BOOKING_ID_1))
                .thenThrow(new BookingNotFoundException(BOOKING_ID_1));

        String findUrl = String.format(BOOKING_URL_TEMPLATE, USER_ID_1, BOOKING_ID_1);
        String urlTemplate = new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(findUrl).toString();
        mockMvc.perform(get(urlTemplate).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBookingForAlreadyReservedSlotFails() throws Exception {
        String createUrl = String.format(CREATE_BOOKING_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(createUrl).toString();
        when(mockCreateBookingUseCase.book(CREATE_BOOKING_COMMAND))
                .thenThrow(
                        new SlotAlreadyReservedException(SLOT_ID_1, SLOT_USER, ANOTHER_SLOT_USER));

        mockMvc.perform(
                        post(urlTemplate)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isConflict());
    }

    @Test
    void createBookingForSlotNotOpenFails() throws Exception {
        when(mockCreateBookingUseCase.book(CREATE_BOOKING_COMMAND))
                .thenThrow(new SlotNotOpenException(SLOT_ID_1));

        String createUrl = String.format(CREATE_BOOKING_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(createUrl).toString();
        mockMvc.perform(
                        post(urlTemplate)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isPreconditionRequired());
    }

    @Test
    void createBookingForWrongUserFails() throws Exception {
        when(mockCreateBookingUseCase.book(CREATE_BOOKING_COMMAND))
                .thenThrow(new WrongBookingUserException(USER_ID_1, USER_ID_2, BOOKING_ID_1));

        String createUrl = String.format(CREATE_BOOKING_URL_TEMPLATE, ROOM_ID, SLOT_ID_1);
        String urlTemplate =
                new StringJoiner(FORTHSLASH).add(BOOKINGS_URL).add(createUrl).toString();
        mockMvc.perform(
                        post(urlTemplate)
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(CREATE_BOOKING_REQUEST)))
                .andExpect(status().isUnauthorized());
    }
}
