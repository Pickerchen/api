package com.rrioo.smartlife.util;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/29 14:13 1.0
 * @time 2017/9/29 14:13
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/29 14:13
 */

public class NullUtil {
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(byte[] params) {
        return params == null || params.length <= 0;
    }

    public static boolean isEmpty(int[] params) {
        return params == null || params.length <= 0;
    }

    public static boolean isEmpty(float[] params) {
        return params == null || params.length <= 0;
    }

    public static boolean isEmpty(double[] params) {
        return params == null || params.length <= 0;
    }

}
