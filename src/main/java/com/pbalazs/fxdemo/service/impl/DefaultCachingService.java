package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.model.CacheKey;
import com.pbalazs.fxdemo.service.CachingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 4/9/2017.
 */
@Service
public class DefaultCachingService implements CachingService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCachingService.class);

    private final Map<CacheKey, String> cache = new HashMap<>();

    @Override
    public String retrieve(final String ccy, final String date) {
        final String rate = cache.get(new CacheKey(ccy, date));
        if (rate == null) {
            logger.info("No entry found for the {} - {} pair", ccy, date);
        }
        return rate;
    }

    @Override
    public void store(final String ccy, final String date, final String rate) {
        final CacheKey key = new CacheKey(ccy, date);
        cache.put(key, rate);
    }
}
