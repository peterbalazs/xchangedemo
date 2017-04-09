package com.pbalazs.fxdemo.model;

import org.springframework.core.style.ToStringCreator;
import org.springframework.core.style.ToStringStyler;

/**
 * Created by Peter on 4/9/2017.
 */
public class FxResponseModel {

    private String currency;

    private String date;

    private String rate;

    @Override
    public String toString() {
        return new ToStringCreator(this).append("currency", currency)
                .append("date", date)
                .append("rate", rate)
                .toString();
    }

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
