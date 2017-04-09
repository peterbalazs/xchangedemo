package com.pbalazs.fxdemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Peter on 4/9/2017.
 */
public final class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static String validateDate(final String date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        try {
            new SimpleDateFormat(DATE_PATTERN).parse(date);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid date format");
        }

        return date;
    }

}
