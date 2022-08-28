package com.payconiq.qa.e2e;

import com.payconiq.qa.BaseAPI;
import com.payconiq.qa.config.RelativeURI;
import com.payconiq.qa.utils.CommonUtil;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class DeleteBookingTest extends BaseAPI {

    private CommonUtil commonUtil;

    @BeforeEach
    public void createABooking() {

        commonUtil = new CommonUtil();
        bookingId = commonUtil.createABooking();
        header = new HashMap<>();

    }

    /**
     * @TestId - TC007
     * @TestDec Verify user get HTTP 200 response when Delete Booking
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC007 :- Verify user get HTTP 200 response when Delete Booking")
    public void verify_booking_deletion() {

        header.put("Cookie", "token=" + authToken);
        header.put("Content-Type", "application/json");

        ValidatableResponse response = given().headers(header).
                when().
                delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
                then().
                statusCode(200);

        Assertions.assertEquals("Created", response.extract().body().asString());
    }


    /**
     * @TestId - TC008
     * @TestDec Verify user get HTTP 415 response when Delete Booking with invalid Content-Type
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC008 :- Verify user get HTTP 415 response when Delete Booking with invalid Content-Type")
    public void verify_booking_deletion_with_invalid_contentType() {

        header.put("Cookie", "token=" + authToken);

        ValidatableResponse response = given().headers(header).contentType(ContentType.TEXT).
                when().
                delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
                then().
                statusCode(415);

        Assertions.assertEquals("Unsupported Media Type", response.extract().body().asString());
    }

    /**
     * @TestId - TC009
     * @TestDec Verify user get HTTP 401 response when Delete Booking without Cookie header
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC009 :- Verify user get HTTP 401 response when Delete Booking without Cookie header")
    public void verify_booking_deletion_without_cookie_header() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
                when().
                delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
                then().
                statusCode(401);

        Assertions.assertEquals("Unauthorized", response.extract().body().asString());
    }

    /**
     * @TestId - TC010
     * @TestDec Verify user get HTTP 401 response when Delete Booking with invalid Cookie value
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC010 :- Verify user get HTTP 401 response when Delete Booking with invalid Cookie value")
    public void verify_booking_deletion_with_invalid_cookie_value() {

        header.put("Cookie", "token=" + "ABCD123");

        ValidatableResponse response = given().headers(header).contentType(ContentType.JSON).
                when().
                delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
                then().
                statusCode(401);

        Assertions.assertEquals("Unauthorized", response.extract().body().asString());
    }

    /**
     * @TestId - TC011
     * @TestDec Verify user get HTTP 400 response when Delete Booking - Invalid bookingID
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC011 :- Verify user get HTTP 400 response when Delete Booking - Invalid bookingID")
    public void verify_booking_deletion_with_invalid_bookingID() {

        header.put("Cookie", "token=" + authToken);

        ValidatableResponse response = given().headers(header).contentType(ContentType.JSON).
                when().
                delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", "abc123")).
                then().
                statusCode(400);

        Assertions.assertEquals("Bad Request", response.extract().body().asString());
    }

    /**
     * @TestId - TC012
     * @TestDec Verify user get HTTP 404 response when Delete Booking - bookingID is not found
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC012 :- Verify user get HTTP 404 response when Delete Booking - bookingID is not found")
    public void verify_booking_deletion_without_bookingID() {

        header.put("Cookie", "token=" + authToken);

        ValidatableResponse response = given().headers(header).contentType(ContentType.JSON).
                when().
                delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", "")).
                then().
                statusCode(404);

        Assertions.assertEquals("Not Found", response.extract().body().asString());
    }

    /**
     * @TestId - TC013
     * @TestDec Verify user get HTTP 405 response when Delete Booking - Invalid HTTP Method
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the DELETE request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC013 :- Verify user get HTTP 405 response when Delete Booking - Invalid HTTP Method")
    public void verify_booking_deletion_with_invalid_httpMethod() {

        header.put("Cookie", "token=" + authToken);

        ValidatableResponse response = given().headers(header).contentType(ContentType.JSON).
                when().
                post(RelativeURI.DELETE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
                then().
                statusCode(405);

        Assertions.assertEquals("Method Not Allowed", response.extract().body().asString());
    }

    @AfterEach
    public void dataCleanUp() {

        commonUtil.dataCleanUp(header, authToken, bookingId);
    }
}
