package com.payconiq.qa.config;

public class RelativeURI {

    public static final String POST_CREATE_TOKEN = "/auth";
    public static final String POST_CREATE_BOOKING = "/booking";
    public static final String GET_BOOKING_BYID = "/booking/{booking_id}";
    public static final String GET_ALL_BOOKINGS = "/booking";
    public static final String PUT_UPDATE_BOOKING = "/booking/{booking_id}";
    public static final String DELETE_BOOKING = "/booking/{booking_id}";

    public static final String POST_CREATE_BOOKING_INVALID = "/bookings";
}
