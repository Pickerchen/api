package com.sen5.lib.util;

import android.text.TextUtils;
import android.util.Base64;

/**
 * Created by zhurongkun on 2017/8/23.
 */

public class Base64Util {
    public static String encodeToString(byte[] array) {
        if (array == null)
            return null;
        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    private Base64Util() {
    }
}
