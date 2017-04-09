package com.pbalazs.fxdemo.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Peter on 4/9/2017.
 */
public class DateUtilsTest {

    @Test
    public void test_validateDate_success() {
        final String result = DateUtils.validateDate("2009-12-21");
        assertEquals("2009-12-21", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_validateDate_null() {
        DateUtils.validateDate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_validateDate_fail() {
        DateUtils.validateDate("2007-05");
    }
}
