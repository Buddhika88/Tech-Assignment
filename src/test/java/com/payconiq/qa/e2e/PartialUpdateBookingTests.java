package com.payconiq.qa.e2e;

import com.payconiq.qa.BaseAPI;
import com.payconiq.qa.config.RelativeURI;
import com.payconiq.qa.data.CreateBookingData;
import com.payconiq.qa.models.BookingDataRequestDTO;
import com.payconiq.qa.models.BookingDatesRequestDTO;
import com.payconiq.qa.utils.CommonUtil;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PartialUpdateBookingTests extends BaseAPI {

    private CommonUtil commonUtil;
    private CreateBookingData bookingData;
    private BookingDatesRequestDTO bookingDatesRequestDTO;
    private BookingDataRequestDTO bookingDataRequestDTO;

    @BeforeEach
    public void createABooking() {

        commonUtil = new CommonUtil();
        bookingId = commonUtil.createABooking();
        header = new HashMap<>();
    }

    /**
     * @TestId - TC024
     * @TestDec Verify user get HTTP 200 response when Partial Update a Booking with firstName field
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC024 :- Verify user get HTTP 200 response when Partial Update a Booking with firstName field")
    public void partial_update_booking_with_valid_booking_details() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + authToken);

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(200);

        Assertions.assertEquals("Jane", response.extract().body().path("firstname"));
    }

    /**
     * @TestId - TC025
     * @TestDec Verify user get HTTP 415 response when Partial Update a Booking with invalid Content-Type
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC025 :- Verify user get HTTP 415 response when Partial Update a Booking with invalid Content-Type")
    public void partial_update_booking_with_invalid_contentType() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + authToken);

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.TEXT).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(415);

        Assertions.assertEquals("Unsupported Media Type", response.extract().body().asString());
    }

    /**
     * @TestId - TC026
     * @TestDec Verify user get HTTP 401 response when Partial Update a Booking without Cookie header
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC026 :- Verify user get HTTP 401 response when Partial Update a Booking without Cookie header")
    public void partial_update_booking_without_cookie_header() {

        header = headerCreation();
        header.put("Accept", "application/json");

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(401);

        Assertions.assertEquals("Unauthorized", response.extract().body().asString());
    }

    /**
     * @TestId - TC027
     * @TestDec Verify user get HTTP 401 response when Partial Update a Booking with invalid Cookie value
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC027 :- Verify user get HTTP 401 response when Partial Update a Booking with invalid Cookie value")
    public void partial_update_booking_with_invalid_cookie_value() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + "abc123");

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(401);

        Assertions.assertEquals("Unauthorized", response.extract().body().asString());
    }

    /**
     * @TestId - TC028
     * @TestDec Verify user get HTTP 400 response when Partial Update a Booking - Invalid bookingID
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC028 :- Verify user get HTTP 400 response when Partial Update a Booking - Invalid bookingID")
    public void partial_update_booking_with_invalid_bookingId() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + authToken);

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", "abcd1234")).
            then().
            statusCode(400);

        Assertions.assertEquals("Bad Request", response.extract().body().asString());
    }

    /**
     * @TestId - TC029
     * @TestDec Verify user get HTTP 404 response when Partial Update a Booking - bookingID is not found
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC029 :- Verify user get HTTP 404 response when Partial Update a Booking - bookingID is not found")
    public void partial_update_booking_without_bookingId() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + authToken);

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", "")).
            then().
            statusCode(404);

        Assertions.assertEquals("Record Not Found", response.extract().body().asString());
    }

    /**
     * @TestId - TC030
     * @TestDec Verify user get HTTP 400 response when Partial Update a Booking with invalid booking details
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the PUT request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC030 :- Verify user get HTTP 400 response when Partial Update a Booking with invalid booking details")
    public void partial_update_booking_with_invalid_bookingData() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + authToken);

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin("2020-01-01T00:00:987gf").checkout("2020-01-05T00:00:987gf").build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            put(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", "")).
            then().
            statusCode(400);

        Assertions.assertEquals("Invalid date", response.extract().body().asString());
    }

    /**
     * @TestId - TC031
     * @TestDec Verify user get HTTP 405 response when Partial Update a Booking - Invalid HTTP Method
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the POST request instead of a PUT
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC031 :- Verify user get HTTP 405 response when Partial Update a Booking - Invalid HTTP Method")
    public void partial_Update_Booking_with_Invalid_HttpMethod() {

        header = headerCreation();
        header.put("Accept", "application/json");
        header.put("Cookie", "token=" + authToken);

        bookingData = commonUtil.getBookingDataObj();

        bookingDatesRequestDTO = BookingDatesRequestDTO.builder().checkin(bookingData.CHECK_IN).checkout(bookingData.CHECK_OUT).build();
        bookingDataRequestDTO = BookingDataRequestDTO.builder().firstname("Jane").
            lastname(bookingData.LAST_NAME).totalprice(bookingData.TOTAL_PRICE).depositpaid(bookingData.DEPOSIT_PAID).
            bookingdates(bookingDatesRequestDTO).additionalneeds(bookingData.ADDITIONAL_NEEDS).build();

        ValidatableResponse response = given().headers(header).body(bookingDataRequestDTO).contentType(ContentType.JSON).
            when().
            post(RelativeURI.PUT_UPDATE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(405);

        Assertions.assertEquals("Method Not Allowed", response.extract().body().asString());
    }

    @AfterEach
    public void dataCleanUp() {

        commonUtil.dataCleanUp(header, authToken, bookingId);
    }
}
