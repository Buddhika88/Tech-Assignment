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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetBookingIdsTests extends BaseAPI {

    private CreateBookingData bookingData;
    private BookingDatesRequestDTO bookingDatesRequestDTO;
    private BookingDataRequestDTO bookingDataRequestDTO;
    private CommonUtil commonUtil;


    @BeforeEach
    public void createABooking() {

        commonUtil = new CommonUtil();
        bookingId = commonUtil.createABooking();
        header = new HashMap<>();
        bookingData = new CreateBookingData();
    }

    /**
     * @TestId - TC019
     * @TestDec Verify user get HTTP 200 response when Get Booking details - All IDs
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Smoke")
    @DisplayName("TC019 :- Verify user get HTTP 200 response when Get Booking details - All IDs")
    public void get_booking_details_with_valid_booking_details() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
                when().
                get(RelativeURI.GET_ALL_BOOKINGS).
                then().
                statusCode(200);

        Assertions.assertTrue(response.extract().body().jsonPath().getList("bookingid").stream().anyMatch(x -> x.equals(this.bookingId)));
    }

    /**
     * @TestId - TC020
     * @TestDec Verify user get HTTP 405 response when Get ALl Booking details - Invalid HTTP Method
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC020 :- Verify user get HTTP 405 response when Get ALl Booking details - Invalid HTTP Method")
    public void get_booking_details_with_invalid_http_method() {

        ValidatableResponse response = given().contentType(ContentType.JSON).
                when().
                post(RelativeURI.GET_ALL_BOOKINGS).
                then().
                statusCode(405);

        Assertions.assertEquals("Method Not Allowed", response.extract().body().asString());
    }

    /**
     * @TestId - TC021
     * @TestDec Verify user get HTTP 200 response when Get Booking details - Filter By Name
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC021 :- Verify user get HTTP 200 response when Get Booking details - Filter By Name")
    public void get_booking_details_by_name() {

        List<Object> bookingIds = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("firstname", bookingData.FIRST_NAME);

        ValidatableResponse response = given().contentType(ContentType.JSON).
                when().
                params(params).
                get(RelativeURI.GET_ALL_BOOKINGS).
                then().
                statusCode(200);

        bookingIds = response.extract().body().jsonPath().getList("bookingid");

        for (Object id : bookingIds) {

            ValidatableResponse res = given().contentType(ContentType.JSON).
                    when().
                    get(RelativeURI.GET_BOOKING_BYID.replace("{booking_id}", id.toString())).
                    then().
                    statusCode(200);

            Assertions.assertEquals(bookingData.FIRST_NAME, res.extract().body().path("firstname"));

        }
    }

    /**
     * @TestId - TC022
     * @TestDec Verify user get HTTP 200 response when Get Booking details- Filter By Name with invalid values
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC022 :- Verify user get HTTP 200 response when Get Booking details- Filter By Name with invalid values")
    public void get_booking_details_by_invalid_name() {

        HashMap<String, String> params = new HashMap<>();
        params.put("firstname", "ZZZ");

        ValidatableResponse response = given().contentType(ContentType.JSON).
                when().
                params(params).
                get(RelativeURI.GET_ALL_BOOKINGS).
                then().
                statusCode(200);

        Assertions.assertTrue(response.extract().body().jsonPath().getList("bookingid").isEmpty());
    }

    /**
     * @TestId - TC023
     * @TestDec Verify user get HTTP 400 response when Get Booking details with invalid Parameters - Filter By Name
     * @implNote Initially Generating the header and
     * Then Building request body using builder
     * Sending the GET request and Validating
     */
    @Test
    @Tag("Regression")
    @DisplayName("TC023 :- Verify user get HTTP 400 response when Get Booking details with invalid Parameters - Filter By Name")
    public void get_booking_details_by_invalid_parameters_filter_by_name() {

        bookingData.generateValidBookingDetails();

        HashMap<String, String> params = new HashMap<>();
        params.put("invalid", bookingData.FIRST_NAME);

        ValidatableResponse response = given().contentType(ContentType.JSON).
                when().
                params(params).
                get(RelativeURI.GET_ALL_BOOKINGS).
                then().
                statusCode(400);

        Assertions.assertEquals("Bad Request", response.extract().body().asString());
    }

    @AfterEach
    public void dataCleanUp() {

        commonUtil.dataCleanUp(header, authToken, bookingId);
    }
}
