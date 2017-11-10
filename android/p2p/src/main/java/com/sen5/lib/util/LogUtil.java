package com.sen5.lib.util;

import android.util.Log;

import com.rrioo.logger.MyLog;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by zhurongkun on 2017/8/18.
 */

public class LogUtil {
    private static final String TAG = "lib_sdk";
    private static final boolean DEBUG = true;

    private static final AtomicBoolean inited = new AtomicBoolean(false);

    public static void i(String msgs) {
//        if (DEBUG)
//            Log.i(TAG, wrap(msgs));
        MyLog.i(msgs);
    }

    private static void checkInited() {
    }

    private LogUtil() {
    }

    public static void e(String s) {
//        if (DEBUG)
//            Log.e(TAG, wrap(s));
        MyLog.e(s);
    }

    private static String wrap(String s) {
        return Thread.currentThread().getName() + " : " + s;
    }

    public static void v(String s) {
//        if (DEBUG)
//            Log.v(TAG, wrap(s));
        MyLog.v(s);
    }

    public static void d(String s) {
        if (DEBUG)
            Log.d(TAG, wrap(s));
//        MyLog.d(s);
    }

    public static void json(String json) {
//        i(json);
        MyLog.json(json);
    }
}
