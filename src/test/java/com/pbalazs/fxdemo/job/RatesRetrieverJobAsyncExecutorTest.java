package com.pbalazs.fxdemo.job;

import com.pbalazs.fxdemo.service.CachingService;
import com.pbalazs.fxdemo.service.RateLoaderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Peter on 4/9/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RatesRetrieverJobAsyncExecutorTest {

    private RatesRetrieverJobAsyncExecutor instance;

    @Mock
    private RateLoaderService mockRateLoaderService;

    @Mock
    private CachingService mockCachingService;

    @Before
    public void init() {
        instance = new RatesRetrieverJobAsyncExecutor();
        ReflectionTestUtils.setField(instance, "rateLoaderService", mockRateLoaderService);
        ReflectionTestUtils.setField(instance, "cachingService", mockCachingService);
    }

    @Test
    public void test_runJob_success() {
        final Map<String, Map<String, String>> rateMap = new HashMap<>();
        final Map<String, String> datedRateMap1 = new HashMap<>();
        final Map<String, String> datedRateMap2 = new HashMap<>();
        datedRateMap1.put("CHF", "1.05");
        datedRateMap1.put("GBP", "0.87");
        rateMap.put("2017-04-09", datedRateMap1);
        datedRateMap2.put("CHF", "1.06");
        datedRateMap2.put("GBP", "0.86");
        rateMap.put("2017-04-07", datedRateMap2);
        when(mockRateLoaderService.retrieveRates()).thenReturn(rateMap);

        instance.runJob();

        verify(mockCachingService, times(1)).store("CHF", "2017-04-07", "1.06");
        verify(mockCachingService, times(1)).store("GBP", "2017-04-07", "0.86");
        verify(mockCachingService, times(1)).store("CHF", "2017-04-09", "1.05");
        verify(mockCachingService, times(1)).store("GBP", "2017-04-09", "0.87");
    }

    @Test
    public void test_runJob_nullRates() {
        when(mockRateLoaderService.retrieveRates()).thenReturn(null);

        instance.runJob();

        verify(mockCachingService, times(0)).store(anyString(), anyString(), anyString());
    }
}
