package com.pbalazs.fxdemo.model;

/**
 * Created by Peter on 4/9/2017.
 */
public class DateRatePair {

    private final String date;

    private final String rate;

    public DateRatePair(final String date, final String rate) {
        this.date = date;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public String getRate() {
        return rate;
    }
}
