package com.testapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testapp.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UploadControllerTest extends TestBase {

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void before() {
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://127.0.0.1:9090";
        RestAssured.port = 9090;
    }

    @Test
    @Description("Should return empty uploads list on progress.")
    void shouldTest() throws IOException, URISyntaxException {
        var header = new Header("X-File-Upload", "demo.zip");
        URL resource = getClass().getClassLoader().getResource("demo.zip");
        File mFile = new File(resource.toURI());
        System.out.println(mFile.exists());
        Response response = given()
                .when()
                .header(header)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(new File(resource.toURI()))
                .post("http://127.0.0.1:9090/api/v1/upload")
                .then()
                .extract().response();
        Response responseProgress = given()
                .when()
                .get("http://127.0.0.1:9090/api/v1/upload/progress")
                .then()
                .extract().response();
        Response responseDuration = given()
                .when()
                .get("http://127.0.0.1:9090/api/v1/upload/duration")
                .then()
                .extract().response();
        assertNotNull(response);
        assertNotNull(responseProgress);
        assertNotNull(responseDuration);
    }

}
