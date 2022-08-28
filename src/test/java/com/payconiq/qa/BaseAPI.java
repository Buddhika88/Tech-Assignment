package com.payconiq.qa;

import com.github.javafaker.Faker;
import com.payconiq.qa.config.Configuration;
import com.payconiq.qa.config.RelativeURI;
import com.payconiq.qa.models.AuthTokenRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.aeonbits.owner.ConfigCache;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BaseAPI {

    public static final Logger logger = LoggerFactory.getLogger(BaseAPI.class);
    public static String authToken;
    protected static Configuration configuration;
    public int bookingId;
    protected HashMap<String, String> header;
    protected Faker faker = new Faker();

    @BeforeAll
    public static void beforeAllTests() {

        configuration = getConfiguration();
        baseURI = configuration.baseURI();
        RestAssured.useRelaxedHTTPSValidation();
        authToken = getBasicAccessToken();
    }

    public static Configuration getConfiguration() {
        return ConfigCache.getOrCreate(Configuration.class);
    }

    public static String getBasicAccessToken() {

        AuthTokenRequestDTO authTokenRequestDTO = AuthTokenRequestDTO.builder().
                username(configuration.userName()).password(configuration.password()).build();
        ValidatableResponse response = given().body(authTokenRequestDTO).contentType(ContentType.JSON).
                when().
                post(RelativeURI.POST_CREATE_TOKEN).
                then().
                statusCode(200);
        return response.extract().body().path("token").toString();
    }

    public HashMap<String, String> headerCreation() {

        HashMap<String, String> header = new HashMap<>();
        return header;

    }
}
