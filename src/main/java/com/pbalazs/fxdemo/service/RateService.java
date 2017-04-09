package com.pbalazs.fxdemo.service;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;
import com.pbalazs.fxdemo.model.DateRatePair;

/**
 * Created by Peter on 4/9/2017.
 */
public interface RateService {

    DateRatePair getRateForCurrencyAndDate(final String currency, final String date) throws RateNotAvailableException;

}
