package com.udacity.filmesfamosos.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DateUtils {

    private static SimpleDateFormat format;

    public static String dateToString(Date date) {
        format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public static String getYearDate(Date date) {
        String dateString = dateToString(date);
        return dateString.substring(6,10);
    }

}
