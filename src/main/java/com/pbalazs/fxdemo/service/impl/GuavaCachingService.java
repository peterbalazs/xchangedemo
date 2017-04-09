package com.pbalazs.fxdemo.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pbalazs.fxdemo.model.CacheKey;
import com.pbalazs.fxdemo.service.CachingService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Peter on 4/9/2017.
 */
@Service
public class GuavaCachingService implements CachingService {

    private final Cache<CacheKey, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(90, TimeUnit.DAYS)
            .build();

    @Override
    public String retrieve(final String ccy, final String date) {
        final CacheKey key = new CacheKey(ccy, date);
        return cache.getIfPresent(key);
    }

    @Override
    public void store(final String ccy, final String date, final String rate) {
        cache.put(new CacheKey(ccy, date), rate);
    }
}
