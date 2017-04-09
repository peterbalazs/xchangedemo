package com.pbalazs.fxdemo.model;

/**
 * Created by Peter on 4/9/2017.
 */
public class FxResponseModel {

    private String currency;

    private String date;

    private String rate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
