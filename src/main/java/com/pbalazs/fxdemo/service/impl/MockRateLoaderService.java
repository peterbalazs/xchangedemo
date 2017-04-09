package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.service.RateLoaderService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 4/9/2017.
 */
@Service
public class MockRateLoaderService implements RateLoaderService {

    @Override
    public Map<String, Map<String, String>> retrieveRates() {
        final Map<String, Map<String, String>> rateMap = new HashMap<>();
        final Map<String, String> datedRateMap1 = new HashMap<>();
        final Map<String, String> datedRateMap2 = new HashMap<>();
        datedRateMap1.put("CHF", "1.05");
        datedRateMap1.put("GBP", "0.87");
        rateMap.put("2017-04-09", datedRateMap1);
        datedRateMap2.put("CHF", "1.06");
        datedRateMap2.put("GBP", "0.86");
        rateMap.put("2017-04-07", datedRateMap2);
        return rateMap;
    }
}
