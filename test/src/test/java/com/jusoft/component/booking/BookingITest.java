package com.jusoft.component.booking;

import com.jusoft.util.HostUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jusoft.component.common.CommonOps.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingITest {

    private static final String USER_ID = "1234";
    private static final String CREATE_BOOKING_REQUEST = "{\"userId\":" + USER_ID + ",\"roomId\":\"1\",\"slotId\":%s}";
    private static final String BOOKINGS_PATH = "/bookings";
    private static final String USER_BOOKINGS_PATH = BOOKINGS_PATH + "/user/{userId}";
    private static final String BOOKING_PATH = BOOKINGS_PATH + "/user/{userId}/booking/{bookingId}";

    private static final Logger log = LoggerFactory.getLogger(BookingITest.class);

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = HostUtils.getHost();
        RestAssured.port = HostUtils.getPort();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void book() {
        // @formatter:off
        int slotId = createSlot();
        given()
                .contentType(ContentType.JSON)
                .body(String.format(CREATE_BOOKING_REQUEST, slotId))
        .when()
                .post(BOOKINGS_PATH)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("bookingId", notNullValue())
                .body("bookingTime", notNullValue())
                .body("slot.roomId", is(ROOM_ID.intValue()))
                .body("slot.slotId",not(nullValue()))
                .body("slot.startDate", is(START_TIME.intValue()))
                .body("slot.endDate", is(END_TIME.intValue()));
        // @formatter:on
    }

    @Test
    public void cancel() {
        int slotId = createSlot();
        int bookingId = createBooking(slotId);

        // @formatter:off
        given()
                .pathParam("userId", USER_ID)
                .pathParam("bookingId", bookingId)
        .when()
                .delete(BOOKING_PATH)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        // @formatter:on
    }

    @Test
    public void find() {
        // @formatter:off
        int slotId = createSlot();
        int bookingId = createBooking(slotId);

        given()
                .pathParam("userId", USER_ID)
                .pathParam("bookingId", bookingId)
        .when()
                .get(BOOKING_PATH)
        .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("bookingId", is(bookingId));
        // @formatter:on
    }

    @Test
    public void getFor() {
        // @formatter:off
        int slotId = createSlot();
        int bookingId = createBooking(slotId);

        given()
                .pathParam("userId", USER_ID)
        .when()
                .get(USER_BOOKINGS_PATH)
        .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("bookings.bookingId", hasItems(bookingId));
        // @formatter:on
    }

    private int createBooking(int slotId) {
        // @formatter:off
        String format = String.format(CREATE_BOOKING_REQUEST, slotId);
        log.info(format);
        return given()
                .contentType(ContentType.JSON)
                .body(format)
                .post(BOOKINGS_PATH)
                .path("bookingId");
        // @formatter:on
    }
}
