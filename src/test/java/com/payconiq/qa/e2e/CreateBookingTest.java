package com.payconiq.qa.e2e;

import com.payconiq.qa.BaseAPI;
import com.payconiq.qa.config.RelativeURI;
import com.payconiq.qa.data.CreateBookingData;
import com.payconiq.qa.models.BookingDataRequestDTO;
import com.payconiq.qa.models.BookingDatesRequestDTO;
import com.payconiq.qa.utils.CommonUtil;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateBookingTest extends BaseAPI {

    private CreateBookingData bookingData;
    private BookingDatesRequestDTO bookingDatesRequestDTO;
    private BookingDataRequestDTO bookingDataRequestDTO;
    private CommonUtil  commonUtil = new CommonUtil();;

    /**
     * @TestId - TC001
     * @TestDec Verify user get HTTP 200 response when Create Booking with valid booking details
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the POST request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC001 :- Verify user get HTTP 200 response when Create Booking with valid booking details")
    public void create_booking_with_valid_booking_details() {

        bookingData = new CreateBookingData();
        bookingData.generateValidBookingDetails();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname(bookingData.FIRST_NAME).
                lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
                bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().body(bookingDataRequestDTO).contentType(ContentType.JSON).
                when().
                post(RelativeURI.POST_CREATE_BOOKING).
                then().
                statusCode(200);

        this.bookingId = response.extract().body().path("bookingid");

        Assertions.assertEquals(bookingData.FIRST_NAME, response.extract().body().path("booking.firstname"));
        Assertions.assertNotNull(bookingId);

        logger.info("TC001 : Cleaning Created Data - BookingID : "+bookingId);
        header = new HashMap<>();
        commonUtil.dataCleanUp(header, authToken, bookingId);
    }

    /**
     * @TestId - TC002
     * @TestDec Verify user get HTTP 415 response when Create Booking with invalid Content-Type
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the POST request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC002 :- Verify user get HTTP 415 response when Create Booking with invalid Content-Type")
    public void create_booking_with_invalid_contentType() {

        bookingData = new CreateBookingData();
        bookingData.generateValidBookingDetails();
        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname(bookingData.FIRST_NAME).
                lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
                bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().body(bookingDataRequestDTO).contentType(ContentType.TEXT).
                when().
                post(RelativeURI.POST_CREATE_BOOKING).
                then().statusCode(415);

        Assertions.assertEquals("Unsupported Media Type", response.extract().body().asString());
    }

    /**
     * @TestId - TC003
     * @TestDec Verify user get HTTP 404 response when Create Booking with invalid endpoint
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the POST request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC003 :- Verify user get HTTP 404 response when Create Booking with invalid endpoint")
    public void create_booking_with_invalid_endPoint() {

        bookingData = new CreateBookingData();
        bookingData.generateValidBookingDetails();
        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname(bookingData.FIRST_NAME).
                lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
                bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().body(bookingDataRequestDTO).contentType(ContentType.JSON).
                when().
                post(RelativeURI.POST_CREATE_BOOKING_INVALID).
                then().
                statusCode(404);

        Assertions.assertEquals("Not Found", response.extract().body().asString());

    }

    /**
     * @TestId - TC004
     * @TestDec Verify user get HTTP 405 response when Create Booking with invalid HTTP method
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PATCH request instead of a POST
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC004 :- Verify user get HTTP 405 response when Create Booking with invalid HTTP method")
    public void create_booking_with_invalid_httpMethod() {

        bookingData = new CreateBookingData();
        bookingData.generateValidBookingDetails();
        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname(bookingData.FIRST_NAME).
                lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
                bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().body(bookingDataRequestDTO).contentType(ContentType.JSON).
                when().
                patch(RelativeURI.POST_CREATE_BOOKING).
                then().
                statusCode(405);

        Assertions.assertEquals("Method Not Allowed", response.extract().body().asString());
    }

    /**
     * @TestId - TC005
     * @TestDec Verify user get HTTP 400 response when Create Booking with invalid booking details by passing Invalid Date
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the POST request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC005 :- Verify user get HTTP 400 response when Create Booking with invalid booking details by passing Invalid Date")
    public void create_booking_with_invalid_bookingDetails() {

        bookingData = new CreateBookingData();
        bookingData.generateValidBookingDetails();
        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin("2020-01-01T00:00:987gf").checkout("2020-01-05T00:00:987gf").build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname(bookingData.FIRST_NAME).
                lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
                bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().body(bookingDataRequestDTO).contentType(ContentType.JSON).
                when().
                post(RelativeURI.POST_CREATE_BOOKING).
                then().
                statusCode(400);

        Assertions.assertEquals("Invalid date", response.extract().body().asString());
    }

    /**
     * @TestId - TC006
     * @TestDec Verify user get HTTP 200 response when Create Booking Schema Validation
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the POST request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC006 :- Verify user get HTTP 200 response when Create Booking Schema Validation")
    public void create_booking_schemaValidation() {



        bookingData = new CreateBookingData();
        bookingData.generateValidBookingDetails();
        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname(bookingData.FIRST_NAME).
                lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
                bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response =  given().body(bookingDataRequestDTO).contentType(ContentType.JSON).
                when().
                post(RelativeURI.POST_CREATE_BOOKING).
                then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schemas/create_Booking_Schema.json"));

        this.bookingId = response.extract().body().path("bookingid");
        logger.info("TC006 : Cleaning Created Data - BookingID : "+bookingId);
        header = new HashMap<>();
        commonUtil.dataCleanUp(header, authToken, bookingId);
    }



}
