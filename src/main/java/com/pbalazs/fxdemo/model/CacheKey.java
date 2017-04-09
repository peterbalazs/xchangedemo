package com.pbalazs.fxdemo.model;

import org.springframework.util.Assert;

/**
 * Created by Peter on 4/9/2017.
 */
public class CacheKey {

    private final String currency;

    private final String date;

    public CacheKey(final String ccy, final String date) {

        Assert.notNull(ccy, "Must specify a non-NULL currency");
        Assert.notNull(date, "Must specify a non-NULL date");

        this.currency = ccy;
        this.date = date;
    }

    @Override
    public int hashCode() {
        return (date + currency).hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof CacheKey)) {
            return false;
        }

        final CacheKey that = (CacheKey) other;
        return date.equals(that.date) && currency.equalsIgnoreCase(that.currency);
    }
}
