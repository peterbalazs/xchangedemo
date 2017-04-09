package com.pbalazs.fxdemo.service.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Peter on 4/9/2017.
 */
public class DefaultCachingServiceTest {

    private DefaultCachingService instance;

    @Before
    public void init() {
        instance = new DefaultCachingService();
    }

    @Test
    public void test_all() {
        String result = instance.retrieve("CHF", "2017-04-09");

        assertNull(result);

        instance.store("CHF", "2017-04-09", "1.01");
        result = instance.retrieve("CHF", "2017-04-09");

        assertEquals("1.01", result);
    }
}
