package com.jasongoetz.restassured;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class CacheResourceIT {

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void cacheTest2() throws Exception {
        when().get("/cache/test").then().statusCode(HttpStatus.SC_OK).body(equalTo("I am a cache"));
    }
}
