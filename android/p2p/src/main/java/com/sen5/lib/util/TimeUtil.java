package com.sen5.lib.util;

import android.content.Context;
import android.text.format.DateUtils;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 10:39 1.0
 * @time 2017/9/30 10:39
 * @project secQreNew3.0 com.sen5.lib.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 10:39
 */

public class TimeUtil {

    public static String formatTime(Context context, long now) {
        return DateUtils.formatDateTime(context, now, DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME
        );
    }
}
