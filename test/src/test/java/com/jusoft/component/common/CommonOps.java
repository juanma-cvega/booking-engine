package com.jusoft.component.common;

import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;

public class CommonOps {

    public static final Long START_TIME = LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC);
    public static final Long END_TIME = LocalDateTime.now().plus(1, ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC);
    public static final Long ROOM_ID = 1L;
    public static final String CREATE_SLOT_REQUEST = "{\"roomId\":" + ROOM_ID + ",\"startTime\":" + START_TIME + ",\"endTime\":" + END_TIME + "}";

    public static final String SLOTS_URL = "/slots/";

    public static int createSlot() {
        return given()
                .body(CREATE_SLOT_REQUEST)
                .contentType(ContentType.JSON)
                .post(SLOTS_URL)
                .path("slotId");
    }
}
