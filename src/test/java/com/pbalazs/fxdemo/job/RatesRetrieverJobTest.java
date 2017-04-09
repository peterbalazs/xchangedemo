package com.pbalazs.fxdemo.job;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Peter on 4/9/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RatesRetrieverJobTest {

    private RatesRetrieverJob instance;

    @Mock
    private RatesRetrieverJobAsyncExecutor mockRatesRetrieverJobAsyncExecutor;

    @Before
    public void init() {
        instance = new RatesRetrieverJob();
        ReflectionTestUtils.setField(instance, "asyncExecutor", mockRatesRetrieverJobAsyncExecutor);
    }

    @Test
    public void test_runDailyJob() {
        instance.runDailyJob();

        verify(mockRatesRetrieverJobAsyncExecutor, times(1)).runDailyJob();
    }
}
