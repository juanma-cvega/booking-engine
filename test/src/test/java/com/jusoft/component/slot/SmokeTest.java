package com.jusoft.component.slot;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

@Slf4j
public class SmokeTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SmokeTest.class);

    @Test
    public void test() {
        log.info("estoy aqui");
        RestAssured.baseURI = "http://192.168.99.100";
        RestAssured.port = 8080;
        RestAssured.get("/bookings/user/1").then().contentType(ContentType.JSON);
    }
}
