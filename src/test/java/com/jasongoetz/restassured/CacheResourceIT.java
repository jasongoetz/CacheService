package com.jasongoetz.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

public class CacheResourceIT {

    private String googleURL = "http://www.google.com";

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void postAndRetrieveWebPageTest() throws URISyntaxException {
        String googleURL = "http://www.google.com";
        String id = postURL(googleURL);
        when().get("/cache/{id}", id).then().statusCode(HttpStatus.SC_OK).body(containsString("Google"));
        when().get("/cache/{id}", id).then().statusCode(HttpStatus.SC_OK).body(containsString("Google")); //Make sure that the inputstream wasn't closed after the first read
    }

    @Test
    public void postAndRetrieveJSONAPITest() throws URISyntaxException {
        String wikiApiUrl = "https://jsonplaceholder.typicode.com/posts/1";
        String id = postURL(wikiApiUrl);
        when().get("/cache/{id}", id).then().statusCode(HttpStatus.SC_OK).body(containsString("userId"));
    }

    @Test
    public void postAndRetrieveImageTest() {
        String imageURL = "http://www.fakestacks.com/images/um_psu_stack.jpg";
        String id = postURL(imageURL);
        when().get("/cache/{id}", id).then().statusCode(HttpStatus.SC_OK).contentType(MediaType.IMAGE_JPEG.toString());
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

    private String postURL(String url) {
        RequestURL requestURL = new RequestURL();
        requestURL.setUrl(url);
        Response response = given().contentType(ContentType.JSON).body(requestURL).expect().statusCode(HttpStatus.SC_CREATED).when().post("/cache");
        String location = response.getHeader("Location");
        return location.substring(location.lastIndexOf("/")+1);
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
