package com.jetpack.xhb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String FORMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss";

    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat sdr = new SimpleDateFormat(FORMAT_YMDHMS, Locale.CHINA);
        Date date = sdr.parse(time);
        return date.getTime();
    }

}
