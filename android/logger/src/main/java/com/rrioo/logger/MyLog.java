package com.rrioo.logger;


import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/25 9:42 1.0
 * @time 2017/9/25 9:42
 * @project secQreNew3.0 com.rrioo.logger
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/25 9:42
 */
public class MyLog {

    private static final String TAG = "secQre";
    public static boolean mDebug = true;
    private static final int JSON_INDENT = 2;

    /**
     * initialize the logger.
     */
    public static void init() {
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .tag(TAG)
                .showThreadInfo(false)
                .logStrategy(new LogStrategy() {
                    @Override
                    public void log(int priority, String tag, String message) {
                        if (priority < Log.INFO) {
                            Log.println(Log.INFO, tag, message);
                        } else {
                            Log.println(priority, tag, message);
                        }
                    }
                })
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return mDebug && super.isLoggable(priority, tag);
            }
        });
    }

    /**
     * log.v
     *
     * @param msg
     */
    public static void v(String msg) {
        Logger.v(msg);
    }

    /**
     * log.i
     *
     * @param msg
     */
    public static void i(String msg) {
        Logger.i(msg);
    }

    /**
     * log.d
     *
     * @param msg
     */
    public static void d(String msg) {
        Logger.d(msg);
    }

    /**
     * log.d
     *
     * @param obj
     */
    public static void d(Object obj) {
        Logger.d(obj);
    }

    /**
     * log.w
     *
     * @param msg
     */
    public static void w(String msg) {
        Logger.w(msg);
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(Throwable e) {
        Logger.e(e, "");
    }

    /**
     * log.wtf ASSERT级别
     *
     * @param msg
     */
    public static void wtf(String msg) {
        Logger.wtf(msg);
    }

    /**
     * log.json
     *
     * @param jsonStr
     */
    public static void json(String jsonStr) {
        _json(jsonStr);
    }

    private static void _json(String json) {
        if (TextUtils.isEmpty(json)) {
            i("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                i(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                i(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e("Invalid Json");
        }
    }

    /**
     * log.xml
     *
     * @param xmlStr
     */
    public static void xml(String xmlStr) {
        Logger.xml(xmlStr);
    }

    public static void object(Object object) {
        Logger.d(object);
    }
}
