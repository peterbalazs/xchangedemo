package com.pbalazs.fxdemo.service;

/**
 * Created by Peter on 4/9/2017.
 */
public interface CachingService {

    String retrieve(final String ccy, final String date);

    void store(final String ccy, final String date, final String rate);
}
