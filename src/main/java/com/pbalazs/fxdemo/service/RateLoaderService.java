package com.pbalazs.fxdemo.service;

import java.util.Map;

/**
 * Created by Peter on 4/9/2017.
 */
public interface RateLoaderService {

    /**
     * @return a {@link Map} have as key a date and as value another Map. This one has as key the currency, while
     * the value if the rate
     */
    Map<String, Map<String, String>> retrieveRates();
}
