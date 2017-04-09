package com.pbalazs.fxdemo.job;

import com.pbalazs.fxdemo.service.CachingService;
import com.pbalazs.fxdemo.service.RateLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Peter on 4/9/2017.
 */
@Component
public class RatesRetrieverJobAsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(RatesRetrieverJobAsyncExecutor.class);

    @Autowired
    private RateLoaderService rateLoaderService;

    @Autowired
    private CachingService cachingService;

    @Async
    public void runJob() {
        logger.info("Running job to load rates from the provider");

        final Map<String, Map<String, String>> rates = rateLoaderService.retrieveRates();
        if (rates == null || rates.size() == 0) {
            logger.error("The rates were empty!");
            return;
        }

        rates.forEach((date, ratesMap) -> {
            if (ratesMap != null) {
                ratesMap.forEach((ccy, rate) -> {
                    logger.info("Storing exchange rate {} for currency {} on {}", rate, ccy, date);
                    cachingService.store(ccy, date, rate);
                });
            }
        });
    }
}
