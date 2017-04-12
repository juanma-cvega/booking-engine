package com.jusoft.component.slot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SlotITest {

    private static final String SLOTS_URL = "/slots/";
    private static final String FIND_SLOT_URL = SLOTS_URL + "room/{roomId}/slot/{slotId}";
    private static final String GET_SLOTS_URL = SLOTS_URL + "room/{roomId}";
    private static final Long START_TIME = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    private static final Long END_TIME = LocalDateTime.now().plus(1, ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC);
    private static final long ROOM_ID = 1L;
    private static final String CREATE_SLOT_REQUEST = "{\"roomId\":" + ROOM_ID + ",\"startTime\":" + START_TIME + ",\"endTime\":" + END_TIME + "}";
    private static final String DEFAULT_HOST = "192.168.99.100";
    private static final int DEFAULT_PORT = 8080;
    private static final String HTTP = "http://";

    private static final Logger log = LoggerFactory.getLogger(SlotITest.class);

    @BeforeClass
    public static void setup() {
        String host = System.getProperty("host");
        host = host == null ? DEFAULT_HOST : host;
        RestAssured.baseURI = HTTP + host;

        String port = System.getProperty("port");
        RestAssured.port = port == null || !NumberUtils.isCreatable(port) ? DEFAULT_PORT : Integer.parseInt(port);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        log.info("Host and port used http://{}:{}", host, port);
    }

    @Test
    public void get() {
        // @formatter:off
        int slotId = createSlot();

        given()
                .pathParam("roomId", ROOM_ID)
        .when()
                .get(GET_SLOTS_URL)
        .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("slots.slotId", hasItems(slotId));
        // @formatter:on
    }

    @Test
    public void create() {
        // @formatter:off
        given()
                .body(CREATE_SLOT_REQUEST)
                .contentType(ContentType.JSON)
        .when()
                .post(SLOTS_URL)
        .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_CREATED)
                .body("roomId", is(1))
                .body("slotId",not(nullValue()))
                .body("startDate", is(START_TIME.intValue()))
                .body("endDate", is(END_TIME.intValue()));
        // @formatter:on
    }

    @Test
    public void find() {
        // @formatter:off
        int slotId = createSlot();

        given()
                .pathParam("roomId", ROOM_ID)
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
        return given()
                .body(CREATE_SLOT_REQUEST)
                .contentType(ContentType.JSON)
                .post(SLOTS_URL).path("slotId");
    }
}
