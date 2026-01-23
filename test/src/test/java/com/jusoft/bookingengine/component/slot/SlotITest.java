package com.jusoft.bookingengine.component.slot;

import static com.jusoft.bookingengine.component.common.CommonOps.CREATE_SLOT_REQUEST;
import static com.jusoft.bookingengine.component.common.CommonOps.END_TIME;
import static com.jusoft.bookingengine.component.common.CommonOps.ROOM_ID;
import static com.jusoft.bookingengine.component.common.CommonOps.SLOTS_URL;
import static com.jusoft.bookingengine.component.common.CommonOps.START_TIME;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.jusoft.bookingengine.util.HostUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SlotITest {

    private static final String FIND_SLOT_URL = SLOTS_URL + "room/{roomId}/slot/{slotId}";
    private static final String GET_SLOTS_URL = SLOTS_URL + "room/{roomId}";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = HostUtils.getHost();
        RestAssured.port = HostUtils.getPort();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void get() {
        // @formatter:off
        int slotId = createSlot();

        given().pathParam("roomId", ROOM_ID)
                .when()
                .get(GET_SLOTS_URL)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("slots.slotId", hasItems(slotId));
        // @formatter:on
    }

    @Test
    void create() {
        // @formatter:off
        given().body(CREATE_SLOT_REQUEST)
                .contentType(ContentType.JSON)
                .when()
                .post(SLOTS_URL)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_CREATED)
                .body("roomId", is(ROOM_ID.intValue()))
                .body("slotId", not(nullValue()))
                .body("startDate", is(START_TIME.intValue()))
                .body("endDate", is(END_TIME.intValue()));
        // @formatter:on
    }

    @Test
    void find() {
        // @formatter:off
        int slotId = createSlot();

        given().pathParam("roomId", ROOM_ID)
                .pathParam("slotId", slotId)
                .when()
                .get(FIND_SLOT_URL)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("slotId", is(slotId));
        // @formatter:on
    }

    private int createSlot() {
        return given().body(CREATE_SLOT_REQUEST)
                .contentType(ContentType.JSON)
                .post(SLOTS_URL)
                .path("slotId");
    }
}
