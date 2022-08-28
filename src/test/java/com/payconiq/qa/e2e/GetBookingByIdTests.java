package com.payconiq.qa.e2e;

import com.payconiq.qa.BaseAPI;
import com.payconiq.qa.config.RelativeURI;
import com.payconiq.qa.data.CreateBookingData;
import com.payconiq.qa.utils.CommonUtil;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetBookingByIdTests extends BaseAPI {

    private CommonUtil commonUtil;
    private CreateBookingData bookingData;

    @BeforeEach
    public void createABooking() {

        commonUtil = new CommonUtil();
        bookingId = commonUtil.createABooking();
        header = new HashMap<>();
        bookingData = new CreateBookingData();

    }

    /**
     * @TestId - TC014
     * @TestDec Verify user get HTTP 200 response when Get Booking details - specific ID
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC014 :- Verify user get HTTP 200 response when Get Booking details - specific ID")
    public void get_booking_details_with_valid_bookingId() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
            when().
            get(RelativeURI.GET_BOOKING_BYID.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(200);

        Assertions.assertEquals(commonUtil.getBookingDataObj().FIRST_NAME, response.extract().body().path("firstname"));

    }

    /**
     * @TestId - TC015
     * @TestDec Verify user get HTTP 405 response when Get Booking details - Invalid HTTP Method
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the Post request instead of GET
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC015 :- Verify user get HTTP 405 response when Get Booking details - Invalid HTTP Method")
    public void get_booking_details_with_invalid_http_method() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
            when().
            post(RelativeURI.GET_BOOKING_BYID.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(405);

        Assertions.assertEquals("Method Not Allowed", response.extract().body().asString());
    }

    /**
     * @TestId - TC016
     * @TestDec Verify user get HTTP 405 response when get Booking details - Invalid bookingID
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC016 :- Verify user get HTTP 405 response when get Booking details - Invalid bookingID")
    public void verify_booking_deletion_with_invalid_bookingID() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
            when().
            get(RelativeURI.GET_BOOKING_BYID.replace("{booking_id}", "abc123")).
            then().
            statusCode(405);

        Assertions.assertEquals("Method Not Allowed", response.extract().body().asString());
    }

    /**
     * @TestId - TC017
     * @TestDec Verify user get HTTP 404 response when get Booking details - bookingID is not found
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC017 :- Verify user get HTTP 404 response when get Booking details - bookingID is not found")
    public void verify_booking_deletion_without_bookingID() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
            when().
            get(RelativeURI.GET_BOOKING_BYID.replace("{booking_id}", "")).
            then().
            statusCode(404);

        Assertions.assertEquals("Not Found", response.extract().body().asString());
    }

    /**
     * @TestId - TC018
     * @TestDec Verify user get HTTP 200 response when Get Booking by Id Schema Validation
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the Get request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC018 :- Verify user get HTTP 200 response when Get Booking by Id Schema Validation")
    public void get_booking_by_id_schemaValidation() {

        given().
            when().
            get(RelativeURI.GET_BOOKING_BYID.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(200).
            body(matchesJsonSchemaInClasspath("schemas/get_Booking_By_Id_Schema.json"));
    }


    @AfterEach
    public void dataCleanUp() {

        commonUtil.dataCleanUp(header, authToken, bookingId);
    }
}
