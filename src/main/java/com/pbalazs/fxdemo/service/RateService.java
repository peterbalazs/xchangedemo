package com.pbalazs.fxdemo.service;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;

/**
 * Created by Peter on 4/9/2017.
 */
public interface RateService {

    String getRateForCurrencyAndDate(final String currency, final String date) throws RateNotAvailableException;

}
