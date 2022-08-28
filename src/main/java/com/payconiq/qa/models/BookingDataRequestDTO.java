package com.payconiq.qa.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingDataRequestDTO {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDatesRequestDTO bookingdates;
    private String additionalneeds;
}
