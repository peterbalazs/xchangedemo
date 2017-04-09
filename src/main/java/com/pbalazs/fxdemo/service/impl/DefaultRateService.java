package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;
import com.pbalazs.fxdemo.service.CachingService;
import com.pbalazs.fxdemo.service.RateService;
import com.pbalazs.fxdemo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Peter on 4/9/2017.
 */
public class DefaultRateService implements RateService {

    @Autowired
    private CachingService cachingService;

    @Override
    public String getRateForCurrencyAndDate(final String currency, final String date) throws RateNotAvailableException {
        final String validatedDate = DateUtils.validateDate(date);
        final String rate = cachingService.retrieve(currency, date);

        if (rate == null) {
            throw new RateNotAvailableException();
        }

        return rate;
    }

}
