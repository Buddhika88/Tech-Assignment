package com.payconiq.qa.data;

import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

public class CreateBookingData {

    public String FIRST_NAME = "Buddhika";
    public String LAST_NAME = "Senanayake";
    public int TOTAL_PRICE = 785;
    public boolean DEPOSIT_PAID = true;
    public String CHECK_IN = "2022-10-01";
    public String CHECK_OUT = "2022-10-03";
    public String ADDITIONAL_NEEDS = "Breakfast";

    Faker faker;

    public void generateValidBookingDetails(){
        faker = new Faker();
        this.FIRST_NAME = faker.name().firstName();
        this.LAST_NAME = faker.name().lastName();
        this.TOTAL_PRICE = faker.number().numberBetween(0,10000);
        this.DEPOSIT_PAID = faker.bool().bool();
        this.CHECK_IN = "2022-10-10";
        this.CHECK_OUT = "2022-10-14";
        this.ADDITIONAL_NEEDS = faker.food().dish();
    }
}
