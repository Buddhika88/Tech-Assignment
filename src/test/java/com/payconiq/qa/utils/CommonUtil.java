package com.payconiq.qa.utils;

import com.payconiq.qa.config.RelativeURI;
import com.payconiq.qa.data.CreateBookingData;
import com.payconiq.qa.models.BookingDataRequestDTO;
import com.payconiq.qa.models.BookingDatesRequestDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CommonUtil {

    public CreateBookingData bookingData;
    private BookingDatesRequestDTO bookingDatesRequestDTO;
    private BookingDataRequestDTO bookingDataRequestDTO;

    public int createABooking() {

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

        return response.extract().body().path("bookingid");
    }

    public void dataCleanUp(HashMap<String, String> header, String authToken, int bookingId) {

        header.put("Cookie", "token=" + authToken);

        given().headers(header).contentType(ContentType.JSON).
            when().
            delete(RelativeURI.DELETE_BOOKING.replace("{booking_id}", String.valueOf(bookingId))).
            then().
            statusCode(201);
    }

    public CreateBookingData getBookingDataObj() {
        return bookingData;
    }
}
