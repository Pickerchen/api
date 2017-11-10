package com.rrioo.smartlife.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/9 16:23 1.0
 * @time 2017/10/9 16:23
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/9 16:23
 */

public class DateTimeUtil {
    public static final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24L;
    public static final String PATTERN_MM_DD_HH_MM = "MM/dd HH:mm";
    public static final String PATTERN_HH_MM = "HH:mm";

    public static String getTimeString(long timeMilious, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timeMilious));
    }
}
