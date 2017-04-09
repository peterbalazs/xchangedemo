package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;
import com.pbalazs.fxdemo.model.DateRatePair;
import com.pbalazs.fxdemo.service.CachingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Peter on 4/9/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultRateServiceTest {

    private DefaultRateService instance;

    @Mock
    private CachingService mockCachingService;

    @Before
    public void init() {
        instance = new DefaultRateService();
        ReflectionTestUtils.setField(instance, "cachingService", mockCachingService);
        ReflectionTestUtils.setField(instance, "daysToCheckInThePast", 4);
    }

    @Test
    public void test_getRateForCurrencyAndDate_happyFlow() throws Exception {
        when(mockCachingService.retrieve("CHF", "2017-04-09")).thenReturn("1.056356");

        final DateRatePair result = instance.getRateForCurrencyAndDate("CHF", "2017-04-09");

        assertNotNull(result);
        assertEquals("1.056356", result.getRate());
        assertEquals("2017-04-09", result.getDate());
    }

    @Test
    public void test_getRateForCurrencyAndDate_bankingHolidays() throws Exception {
        when(mockCachingService.retrieve("CHF", "2017-04-07")).thenReturn("1.05555");

        final DateRatePair result = instance.getRateForCurrencyAndDate("CHF", "2017-04-09");

        verify(mockCachingService, times(1)).retrieve("CHF", "2017-04-09");
        verify(mockCachingService, times(1)).retrieve("CHF", "2017-04-08");
        verify(mockCachingService, times(0)).retrieve("CHF", "2017-04-06");
    }

    @Test(expected = RateNotAvailableException.class)
    public void test_getRateForCurrencyAndDate_notFound() throws Exception {
        when(mockCachingService.retrieve("CHF", "2017-04-09")).thenReturn("1.056356");

        instance.getRateForCurrencyAndDate("CHF", "2017-04-08");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getRateForCurrencyAndDate_invalidDateFormat() throws Exception {
        instance.getRateForCurrencyAndDate("CHF", "jdhskjjfk");
    }
}
