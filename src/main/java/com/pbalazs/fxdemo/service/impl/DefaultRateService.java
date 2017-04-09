package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;
import com.pbalazs.fxdemo.model.DateRatePair;
import com.pbalazs.fxdemo.service.CachingService;
import com.pbalazs.fxdemo.service.RateService;
import com.pbalazs.fxdemo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Peter on 4/9/2017.
 */
@Service
public class DefaultRateService implements RateService {

    @Autowired
    private CachingService cachingService;

    @Value("${rates.banking.holidays.max.days}")
    private int daysToCheckInThePast;

    @Override
    public DateRatePair getRateForCurrencyAndDate(final String currency, final String date) throws RateNotAvailableException {
        final String validatedDate = DateUtils.validateDate(date);
        final List<String> dateList = buildPossibleDatesArray(date);

        for (String possibleDate : dateList) {
            final String rate = cachingService.retrieve(currency, possibleDate);
            if (rate != null) {
                return new DateRatePair(possibleDate, rate);
            }
        };

        throw new RateNotAvailableException();
    }

    /**
     * Rates are not available on banking holidays, so we need to try previous days.
     */
    private List<String> buildPossibleDatesArray(final String date) {
        final List<String> result = new ArrayList<>();
        result.add(date);
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_PATTERN);
        try {
            cal.setTime(dateFormat.parse(date));
        } catch (ParseException pe) {
            return result;
        }

        for (int i = 1; i <= daysToCheckInThePast; i++) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            result.add(dateFormat.format(cal.getTime()));
        }

        return result;
    }


}
