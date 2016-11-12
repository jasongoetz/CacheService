package com.jasongoetz.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class CacheResourceIT {

    private String googleURL = "http://www.google.com";

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void postAndRetrieveTest() throws URISyntaxException {
        RequestURL requestURL = new RequestURL();
        requestURL.setUrl(googleURL);
        Response response = given().contentType(ContentType.JSON).body(requestURL).expect().statusCode(HttpStatus.SC_CREATED).when().post("/cache");
        String location = response.getHeader("Location");
        String id = location.substring(location.lastIndexOf("/")+1);
        when().get("/cache/{id}", id).then().statusCode(HttpStatus.SC_OK).body(equalTo(googleURL));
    }

    @Test
    public void emptyUrlTest() {
        RequestURL requestURL = new RequestURL();
        given().contentType(ContentType.JSON).body(requestURL).expect().statusCode(HttpStatus.SC_BAD_REQUEST).when().post("/cache");
    }

    @Test
    public void nullRequestObjectTest() {
        given().contentType(ContentType.JSON).expect().statusCode(HttpStatus.SC_BAD_REQUEST).when().post("/cache");
    }

    @Test
    public void getWithNullResponseTest() {
        when().get("/cache/{id}", 9999).then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private class RequestURL {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
