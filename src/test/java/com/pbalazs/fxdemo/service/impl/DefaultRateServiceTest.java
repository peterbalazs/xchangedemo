package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;
import com.pbalazs.fxdemo.service.CachingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
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
    }

    @Test
    public void test_getRateForCurrencyAndDate_happyFlow() throws Exception {
        when(mockCachingService.retrieve("CHF", "2017-04-09")).thenReturn("1.056356");

        final String result = instance.getRateForCurrencyAndDate("CHF", "2017-04-09");

        assertEquals("1.056356", result);
    }

    @Test(expected = RateNotAvailableException.class)
    public void test_getRateForCurrencyAndDate_notFound() throws Exception {
        when(mockCachingService.retrieve("CHF", "2017-04-09")).thenReturn("1.056356");

        final String result = instance.getRateForCurrencyAndDate("CHF", "2017-04-08");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getRateForCurrencyAndDate_invalidDateFormat() throws Exception {
        instance.getRateForCurrencyAndDate("CHF", "jdhskjjfk");
    }
}
