package com.jusoft.component.slot;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

@Slf4j
public class SmokeITest {

    @Test
    public void test() {
        RestAssured.baseURI = "http://192.168.99.100";
        RestAssured.port = 8080;
        RestAssured.get("/bookings/user/1").then().contentType(ContentType.JSON);
    }
}
