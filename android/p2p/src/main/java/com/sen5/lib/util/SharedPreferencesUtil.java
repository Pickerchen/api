package com.sen5.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedpreference读写工具
 */
public class SharedPreferencesUtil {

    //SharedPreferences保存形式的XML文件名
    private static final String FILE_NAME = "SmartLife";

    public static boolean save(Context context, String key, Object value) {
        SharedPreferences preferences = getPreference(context);
        SharedPreferences.Editor edit = preferences.edit();
        Class<?> aClass = value.getClass();
        if (aClass == String.class){
            edit.putString(key, (String) value);
        }else if(aClass == Integer.class){
            edit.putInt(key, (Integer) value);
        }else if(aClass == Long.class){
            edit.putLong(key, (Long) value);
        }else if (aClass == Float.class){
            edit.putFloat(key, (Float) value);
        }else if (aClass == Boolean.class){
            edit.putBoolean(key, (Boolean) value);
        }else{
            throw new IllegalArgumentException("illegal @param value ,not supported msgType "+value.getClass());
        }

        return edit.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preference = getPreference(context);
        return preference.getString(key,null);
    }

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferencesUtil() {
    }
}





